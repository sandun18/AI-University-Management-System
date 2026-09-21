package com.aiuniversity.authservice.dto;

import com.aiuniversity.authservice.model.Role;
import com.aiuniversity.authservice.model.User;

public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;

    // ─── Factory Method ───────────────────────────────────────────────────────────

    public static LoginResponse from(User user, String message) {
        LoginResponse response = new LoginResponse();
        response.id       = user.getId();
        response.username = user.getUsername();
        response.email    = user.getEmail();
        response.role     = user.getRole();
        response.message  = message;
        return response;
    }

    // ─── No-Argument Constructor ──────────────────────────────────────────────────

    public LoginResponse() {
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

    public String getMessage() {
        return message;
    }
}
