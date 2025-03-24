package com.Graduation.EduTracker.Models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String firstname;
    private String lastname;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String phoneNumber;
    private boolean active;

        // Roles for user
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "Users_Roles" ,
            joinColumns = @JoinColumn(name = "User_id")
            , inverseJoinColumns = @JoinColumn(name = "Role_id")
    )
    private List<Role> roles =new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.getRoles().forEach(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString()));
        return authorities;
    }


}
