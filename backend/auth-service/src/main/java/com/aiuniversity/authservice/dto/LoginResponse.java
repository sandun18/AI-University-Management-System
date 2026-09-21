package com.aiuniversity.authservice.dto;

import com.aiuniversity.authservice.model.Role;
import com.aiuniversity.authservice.model.User;

public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;
    private String token;

    // ─── Factory Methods ──────────────────────────────────────────────────────────

    public static LoginResponse from(User user, String message, String token) {
        LoginResponse response = new LoginResponse();
        response.id       = user.getId();
        response.username = user.getUsername();
        response.email    = user.getEmail();
        response.role     = user.getRole();
        response.message  = message;
        response.token    = token;
        return response;
    }

    public static LoginResponse from(User user, String message) {
        return from(user, message, null);
    }

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public LoginResponse() {
    }

    public LoginResponse(Long id, String username, String email, Role role, String message, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
        this.token = token;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
