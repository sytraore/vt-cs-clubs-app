package com.vtcsclubs.backend.models;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "club_admins")
public class AdminUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Many admins can belong to 1 club
    // foreign key column is named club_id
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public AdminUser() {
    }

    public AdminUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AdminUser(String email, String password, Club club) {
        this.email = email;
        this.password = password;
        this.club = club;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // We don't have roles yet, so we'll return an empty list.
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // it gets user email instead. The name getUsername is to conform with Spring Security's UserDetails interface method name
    @Override
    public String getUsername() {
        return this.email;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    // For this project, we can assume accounts are always valid.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // --- Getters and Setters ---
    public Long getAminId() {
        return adminId;
    }

    public void setAdminId(Long id) {
        this.adminId = id;
    }

    public void setUserEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
