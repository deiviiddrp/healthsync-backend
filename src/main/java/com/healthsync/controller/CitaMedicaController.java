package com.healthsync.controller;

import com.healthsync.dto.CitaMedicaResponseDto;
import com.healthsync.model.CitaMedica;
import com.healthsync.repository.CitaMedicaRepository;
import com.healthsync.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaMedicaController {

    private final CitaMedicaRepository citaRepo;
    private final UsuarioRepository usuarioRepo;

    @GetMapping
    public ResponseEntity<List<CitaMedicaResponseDto>> getCitas(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) String estado) {
        var usuario = usuarioRepo.findByEmail(user.getUsername()).orElseThrow();
        List<CitaMedica> citas = estado != null
            ? citaRepo.findByUsuarioIdAndEstado(usuario.getId(),
                CitaMedica.EstadoCita.valueOf(estado))
            : citaRepo.findByUsuarioIdOrderByFechaHoraDesc(usuario.getId());
        return ResponseEntity.ok(
            citas.stream().map(CitaMedicaResponseDto::fromEntity).toList());
    }

    @PostMapping
    public ResponseEntity<CitaMedicaResponseDto> crearCita(
            @AuthenticationPrincipal UserDetails user,
            @RequestBody CitaMedica cita) {
        var usuario = usuarioRepo.findByEmail(user.getUsername()).orElseThrow();
        cita.setUsuario(usuario);
        if (cita.getEstado() == null) {
            cita.setEstado(CitaMedica.EstadoCita.PENDIENTE);
        }
        CitaMedica guardada = citaRepo.save(cita);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CitaMedicaResponseDto.fromEntity(guardada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long id) {
        citaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
