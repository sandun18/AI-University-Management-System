package com.aiuniversity.authservice.dto;

import com.aiuniversity.authservice.model.Role;
import com.aiuniversity.authservice.model.User;

import java.time.LocalDateTime;

public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    // ─── Factory Method ───────────────────────────────────────────────────────────

    public static RegisterResponse from(User user) {
        RegisterResponse response = new RegisterResponse();
        response.id        = user.getId();
        response.username  = user.getUsername();
        response.email     = user.getEmail();
        response.role      = user.getRole();
        response.createdAt = user.getCreatedAt();
        return response;
    }

    // ─── No-Argument Constructor ──────────────────────────────────────────────────

    public RegisterResponse() {
    }

    // ─── Getters ──────────────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
