package com.healthsync.repository;

import com.healthsync.model.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {
    List<CitaMedica> findByUsuarioIdOrderByFechaHoraDesc(Long usuarioId);
    List<CitaMedica> findByUsuarioIdAndEstado(
        Long usuarioId, CitaMedica.EstadoCita estado);
}