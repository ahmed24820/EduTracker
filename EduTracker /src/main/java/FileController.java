import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api")
public class FileController {

            private static final String UPLOAD_DIR = "uploads/";
            private static final String API_KEY = System.getenv("VIRUSTOTAL_API_KEY");
            private static final String VIRUS_TOTAL_URL = "https://www.virustotal.com/api/v3/files/";
            private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB limit

            // Cache to store already scanned file results
            private final Map<String, Integer> scanCache = new HashMap<>();

            public FileController() {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) uploadDir.mkdir();
            }

            @PostMapping("/upload")
            public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
                // Check if a file is empty
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of("error", "لم يتم رفع أي ملف!"));
                }

                // Validate file type (only PDF allowed)
                if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Only PDF files are allowed!"));
                }

                // Enforce file size limit
                if (file.getSize() > MAX_FILE_SIZE) {
                    return ResponseEntity.badRequest().body(Map.of("error", "File too large! Max allowed: 10MB"));
                }

                try {
                    // Sanitize filename
                    String safeFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
                    String filePath = UPLOAD_DIR + safeFileName;

                    // Save file securely
                    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                    new File(filePath).setExecutable(false);

                    // Scan the PDF for hidden malware
                    if (containsMaliciousContent(filePath)) {
                        Files.delete(Paths.get(filePath));
                        return ResponseEntity.badRequest().body(Map.of(
                                "file_name", safeFileName,
                                "status", "مرفوض 🚫",
                                "message", "This PDF contains potentially harmful elements! Denied."
                        ));
                    }

                    // Compute SHA-256 hash
                    String fileHash = calculateSHA256(filePath);

                    // Check VirusTotal (cache first)
                    int positives = scanCache.getOrDefault(fileHash, -1);
                    if (positives == -1) {
                        positives = checkVirusTotal(fileHash);
                        scanCache.put(fileHash, positives);
                    }

                    // Reject infected PDFs
                    if (positives > 0) {
                        Files.delete(Paths.get(filePath));
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                                "file_name", safeFileName,
                                "sha256", fileHash,
                                "status", "مرفوض 🚫",
                                "message", "تم الكشف عن " + positives + " تهديدات! الملف غير آمن."
                        ));
                    }

                    return ResponseEntity.ok(Map.of(
                            "file_name", safeFileName,
                            "sha256", fileHash,
                            "status", "مقبول ✅",
                            "message", "الملف آمن ويمكن استخدامه."
                    ));

                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "حدث خطأ أثناء المعالجة!"));
                }
            }

            private boolean containsMaliciousContent(String filePath) {
                try (PDDocument document = PDDocument.load(new File(filePath))) {
                    for (PDPage page : document.getPages()) {
                        for (Iterator<PDStream> it = page.getContentStreams(); it.hasNext(); ) {
                            PDStream stream = it.next();
                            String content = new String(stream.toByteArray());
                            if (content.contains("/JavaScript") || content.contains("/JS") || content.contains("/AA") || content.contains("/Launch")) {
                                return true;
                            }
                        }
                    }
                    return false;
                } catch (Exception e) {
                    return true;
                }
            }

            private String calculateSHA256(String filePath) throws Exception {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        digest.update(buffer, 0, bytesRead);
                    }
                }
                return bytesToHex(digest.digest());
            }

            private String bytesToHex(byte[] bytes) {
                StringBuilder hexString = new StringBuilder();
                for (byte b : bytes) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();
            }

            private int checkVirusTotal(String fileHash) {
                String url = VIRUS_TOTAL_URL + fileHash;
                HttpHeaders headers = new HttpHeaders();
                headers.set("x-apikey", API_KEY);
                HttpEntity<String> requestEntity = new HttpEntity<>(headers);

                RestTemplate restTemplate = new RestTemplate();
                try {
                    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
                    return parseVirusTotalResponse(response);
                } catch (HttpClientErrorException e) {
                    return -1;
                }
            }

            private int parseVirusTotalResponse(ResponseEntity<Map> response) {
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
                    Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
                    Map<String, Integer> stats = (Map<String, Integer>) attributes.get("last_analysis_stats");
                    return stats.getOrDefault("malicious", 0) + stats.getOrDefault("suspicious", 0);
                }
                return 0;
            }

        }


