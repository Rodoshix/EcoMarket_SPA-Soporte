package com.ecomarket.soporte.controller;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.services.SoporteService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soportes")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    // Crear nueva solicitud de soporte
    @PostMapping
    public ResponseEntity<Soporte> crearSoporte(@Valid @RequestBody Soporte soporte) {
        Soporte nuevo = soporteService.crearSolicitud(soporte);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todas las solicitudes de soporte
    @GetMapping
    public ResponseEntity<List<Soporte>> listarSoportes() {
        return ResponseEntity.ok(soporteService.obtenerTodas());
    }

    // Obtener solicitudes por estado (opcional)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Soporte>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(soporteService.obtenerPorEstado(estado));
    }

    // Cambiar el estado de una solicitud
    @PutMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(soporteService.actualizarEstado(id, estado));
    }
}