package com.healthsync.repository;

import com.healthsync.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    List<Alerta> findByUsuarioIdAndActivaAndTipoParametro(
        Long usuarioId, boolean activa, String tipoParametro);
    List<Alerta> findByUsuarioId(Long usuarioId);
}