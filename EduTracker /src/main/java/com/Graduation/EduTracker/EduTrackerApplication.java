package com.Graduation.EduTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class EduTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduTrackerApplication.class, args);
	}

}
