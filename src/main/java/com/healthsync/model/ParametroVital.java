package com.healthsync.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametro_vital")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametroVital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicion")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotBlank
    @Column(name = "tipo_parametro", nullable = false, length = 50)
    private String tipoParametro;

    @NotNull
    @Column(name = "valor", nullable = false, precision = 7, scale = 2)
    private BigDecimal valor;

    @NotBlank
    @Column(name = "unidad", nullable = false, length = 20)
    private String unidad;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Size(max = 500)
    @Column(name = "notas", length = 500)
    private String notas;

    @Column(name = "estado_semaforo", length = 10)
    private String estadoSemaforo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}