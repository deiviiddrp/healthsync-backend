package com.healthsync.controller;

import com.healthsync.dto.*;
import com.healthsync.service.ParametroVitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parametros-vitales")
@RequiredArgsConstructor
public class ParametroVitalController {

    private final ParametroVitalService service;

    @PostMapping("/registro")
    public ResponseEntity<ParametroVitalResponseDto> registrar(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody ParametroVitalRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.registrar(user.getUsername(), dto));
    }

    @GetMapping("/historial")
    public ResponseEntity<Page<ParametroVitalResponseDto>> historial(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(
            service.historial(user.getUsername(), page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long id) {
        service.eliminar(user.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}