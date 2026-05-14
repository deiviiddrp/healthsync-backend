package com.healthsync.service;

import com.healthsync.dto.ParametroVitalRequestDto;
import com.healthsync.dto.ParametroVitalResponseDto;
import com.healthsync.event.NuevaMedicionEvent;
import com.healthsync.exception.ResourceNotFoundException;
import com.healthsync.model.ParametroVital;
import com.healthsync.model.Usuario;
import com.healthsync.repository.ParametroVitalRepository;
import com.healthsync.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParametroVitalService {

    private final ParametroVitalRepository pvRepo;
    private final UsuarioRepository usuarioRepo;
    private final ApplicationEventPublisher eventPublisher;

    private static final Map<String, BigDecimal[]> UMBRALES = Map.of(
        "TENSION_SISTOLICA", new BigDecimal[]{
            new BigDecimal("120"), new BigDecimal("140")},
        "GLUCEMIA", new BigDecimal[]{
            new BigDecimal("100"), new BigDecimal("126")},
        "FC", new BigDecimal[]{
            new BigDecimal("100"), new BigDecimal("120")}
    );

    @Transactional
    public ParametroVitalResponseDto registrar(String email,
            ParametroVitalRequestDto dto) {
        Usuario usuario = usuarioRepo.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Usuario no encontrado"));
        ParametroVital pv = ParametroVital.builder()
            .usuario(usuario)
            .tipoParametro(dto.getTipoParametro())
            .valor(dto.getValor())
            .unidad(dto.getUnidad())
            .fechaHora(dto.getFechaHora() != null
                ? dto.getFechaHora() : LocalDateTime.now())
            .notas(dto.getNotas())
            .estadoSemaforo(calcularSemaforo(
                dto.getTipoParametro(), dto.getValor()))
            .build();
        pv = pvRepo.save(pv);
        log.info("Medicion registrada: usuario={}, tipo={}, valor={}",
            email, dto.getTipoParametro(), dto.getValor());
        eventPublisher.publishEvent(new NuevaMedicionEvent(this, pv));
        return toDto(pv);
    }

    public Page<ParametroVitalResponseDto> historial(String email,
            int page, int size) {
        Usuario usuario = usuarioRepo.findByEmail(email).orElseThrow();
        Pageable pageable = PageRequest.of(page, size,
            Sort.by("fechaHora").descending());
        return pvRepo.findByUsuarioIdOrderByFechaHoraDesc(
            usuario.getId(), pageable).map(this::toDto);
    }

    @Transactional
    public void eliminar(String email, Long id) {
        ParametroVital pv = pvRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Medición no encontrada"));
        if (!pv.getUsuario().getEmail().equals(email)) {
            throw new ResourceNotFoundException("Medición no encontrada");
        }
        pvRepo.delete(pv);
    }

    private String calcularSemaforo(String tipo, BigDecimal valor) {
        BigDecimal[] limites = UMBRALES.get(tipo);
        if (limites == null) return "NORMAL";
        if (valor.compareTo(limites[1]) > 0) return "CRITICO";
        if (valor.compareTo(limites[0]) > 0) return "LIMITE";
        return "NORMAL";
    }

    private ParametroVitalResponseDto toDto(ParametroVital pv) {
        return ParametroVitalResponseDto.builder()
            .id(pv.getId())
            .tipoParametro(pv.getTipoParametro())
            .valor(pv.getValor())
            .unidad(pv.getUnidad())
            .fechaHora(pv.getFechaHora())
            .estadoSemaforo(pv.getEstadoSemaforo())
            .notas(pv.getNotas())
            .build();
    }
}