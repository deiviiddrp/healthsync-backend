package com.healthsync.controller;

import com.healthsync.dto.*;
import com.healthsync.security.JwtTokenProvider;
import com.healthsync.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> register(
            @Valid @RequestBody RegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(usuarioService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword()));
        return ResponseEntity.ok(AuthResponseDto.builder()
            .accessToken(tokenProvider.generateAccessToken(auth.getName()))
            .refreshToken(tokenProvider.generateRefreshToken(auth.getName()))
            .tokenType("Bearer")
            .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(
            @RequestBody RefreshTokenRequestDto dto) {
        if (!tokenProvider.validateToken(dto.getRefreshToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = tokenProvider.getEmailFromToken(dto.getRefreshToken());
        return ResponseEntity.ok(AuthResponseDto.builder()
            .accessToken(tokenProvider.generateAccessToken(email))
            .tokenType("Bearer")
            .build());
    }
}