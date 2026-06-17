package com.healthsync.dto;

import com.healthsync.model.Alerta;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AlertaResponseDto {
    private Long id;
    private String tipoParametro;
    private String operador;
    private BigDecimal valorUmbral;
    private boolean activa;
    private String mensajePersonalizado;
    private LocalDateTime createdAt;

    public static AlertaResponseDto fromEntity(Alerta alerta) {
        return AlertaResponseDto.builder()
            .id(alerta.getId())
            .tipoParametro(alerta.getTipoParametro())
            .operador(alerta.getOperador())
            .valorUmbral(alerta.getValorUmbral())
            .activa(alerta.isActiva())
            .mensajePersonalizado(alerta.getMensajePersonalizado())
            .createdAt(alerta.getCreatedAt())
            .build();
    }
}
