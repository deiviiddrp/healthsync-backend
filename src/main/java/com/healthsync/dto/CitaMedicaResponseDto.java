package com.healthsync.dto;

import com.healthsync.model.CitaMedica;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CitaMedicaResponseDto {
    private Long id;
    private String especialidad;
    private String nombreMedico;
    private String centroMedico;
    private LocalDateTime fechaHora;
    private String notas;
    private String estado;
    private LocalDateTime createdAt;

    public static CitaMedicaResponseDto fromEntity(CitaMedica cita) {
        return CitaMedicaResponseDto.builder()
            .id(cita.getId())
            .especialidad(cita.getEspecialidad())
            .nombreMedico(cita.getNombreMedico())
            .centroMedico(cita.getCentroMedico())
            .fechaHora(cita.getFechaHora())
            .notas(cita.getNotas())
            .estado(cita.getEstado() != null ? cita.getEstado().name() : null)
            .createdAt(cita.getCreatedAt())
            .build();
    }
}
