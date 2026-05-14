package com.healthsync.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "tipo_parametro", nullable = false, length = 50)
    private String tipoParametro;

    @Column(name = "operador", nullable = false, length = 2)
    private String operador;

    @Column(name = "valor_umbral", nullable = false, precision = 7, scale = 2)
    private BigDecimal valorUmbral;

    @Column(name = "activa", nullable = false)
    private boolean activa = true;

    @Column(name = "mensaje_personalizado", length = 300)
    private String mensajePersonalizado;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}