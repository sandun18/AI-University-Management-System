package com.aiuniversity.authservice.controller;

import com.aiuniversity.authservice.dto.LoginRequest;
import com.aiuniversity.authservice.dto.LoginResponse;
import com.aiuniversity.authservice.dto.RegisterRequest;
import com.aiuniversity.authservice.dto.RegisterResponse;
import com.aiuniversity.authservice.model.User;
import com.aiuniversity.authservice.security.JwtService;
import com.aiuniversity.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    // ─── Constructor Injection ────────────────────────────────────────────────────

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    // ─── GET /api/auth/health ─────────────────────────────────────────────────────

    @GetMapping("/health")
    public String health() {
        return "Auth Service is running";
    }

    // ─── POST /api/auth/register ──────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User savedUser = authService.register(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(RegisterResponse.from(savedUser));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }

    // ─── POST /api/auth/login ─────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = authService.login(request);
            String token = jwtService.generateToken(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(LoginResponse.from(user, "Login successful", token));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ex.getMessage());
        }
    }

    // ─── GET /api/auth/admin ──────────────────────────────────────────────────────

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Admin access granted");
    }

    // ─── GET /api/auth/lecturer ───────────────────────────────────────────────────

    @GetMapping("/lecturer")
    @PreAuthorize("hasAnyRole('LECTURER', 'ADMIN')")
    public ResponseEntity<String> lecturerAccess() {
        return ResponseEntity.ok("Lecturer access granted");
    }

    // ─── GET /api/auth/student ────────────────────────────────────────────────────

    @GetMapping("/student")
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    public ResponseEntity<String> studentAccess() {
        return ResponseEntity.ok("Student access granted");
    }
}
