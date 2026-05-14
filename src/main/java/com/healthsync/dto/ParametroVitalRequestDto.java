package com.healthsync.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParametroVitalRequestDto {
    @NotBlank
    private String tipoParametro;
    @NotNull @DecimalMin("0.0") @DecimalMax("9999.99")
    private BigDecimal valor;
    @NotBlank
    private String unidad;
    private LocalDateTime fechaHora;
    private String notas;
}