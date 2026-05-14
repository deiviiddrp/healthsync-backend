package com.healthsync.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
}