package com.healthsync.repository;

import com.healthsync.model.ParametroVital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ParametroVitalRepository extends JpaRepository<ParametroVital, Long> {

    Page<ParametroVital> findByUsuarioIdOrderByFechaHoraDesc(
        Long usuarioId, Pageable pageable);

    List<ParametroVital> findByUsuarioIdAndTipoParametroAndFechaHoraBetween(
        Long usuarioId, String tipoParametro,
        LocalDateTime desde, LocalDateTime hasta);
}