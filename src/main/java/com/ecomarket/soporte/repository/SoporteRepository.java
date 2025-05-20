package com.ecomarket.soporte.repository;

import com.ecomarket.soporte.model.Soporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoporteRepository extends JpaRepository<Soporte, Long> {

    // Permite buscar solicitudes por estado (ej: "pendiente", "resuelto", etc.)
    List<Soporte> findByEstado(String estado);
}