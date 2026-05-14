package com.healthsync.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ParametroVitalResponseDto {
    private Long id;
    private String tipoParametro;
    private BigDecimal valor;
    private String unidad;
    private LocalDateTime fechaHora;
    private String estadoSemaforo;
    private String notas;
}