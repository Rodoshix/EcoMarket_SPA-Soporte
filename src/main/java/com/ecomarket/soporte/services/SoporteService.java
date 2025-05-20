package com.ecomarket.soporte.services;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.repository.SoporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    // Crear una nueva solicitud
    public Soporte crearSolicitud(Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    // Listar todas las solicitudes
    public List<Soporte> obtenerTodas() {
        return soporteRepository.findAll();
    }

    // Filtrar por estado
    public List<Soporte> obtenerPorEstado(String estado) {
        return soporteRepository.findByEstado(estado);
    }

    // Cambiar el estado de una solicitud
    public Soporte actualizarEstado(Long id, String nuevoEstado) {
        Optional<Soporte> soporteOpt = soporteRepository.findById(id);
        if (soporteOpt.isPresent()) {
            Soporte soporte = soporteOpt.get();
            soporte.setEstado(nuevoEstado);
            return soporteRepository.save(soporte);
        } else {
            throw new EntityNotFoundException("Solicitud de soporte no encontrada con ID: " + id);
        }
    }
}