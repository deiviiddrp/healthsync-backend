package com.healthsync.controller;

import com.healthsync.dto.AlertaResponseDto;
import com.healthsync.model.Alerta;
import com.healthsync.repository.AlertaRepository;
import com.healthsync.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaRepository alertaRepo;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public ResponseEntity<List<AlertaResponseDto>> getAlertas(
            @AuthenticationPrincipal UserDetails user) {
        var usuario = usuarioRepo.findByEmail(user.getUsername()).orElseThrow();
        List<Alerta> alertas = alertaRepo.findByUsuarioId(usuario.getId());
        return ResponseEntity.ok(
            alertas.stream().map(AlertaResponseDto::fromEntity).toList());
    }

    @PostMapping
    public ResponseEntity<AlertaResponseDto> crearAlerta(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody Alerta alerta) {
        var usuario = usuarioRepo.findByEmail(user.getUsername()).orElseThrow();
        alerta.setUsuario(usuario);
        alerta.setActiva(true);
        Alerta guardada = alertaRepo.save(alerta);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(AlertaResponseDto.fromEntity(guardada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id) {
        alertaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
