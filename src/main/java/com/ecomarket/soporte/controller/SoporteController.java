package com.ecomarket.soporte.controller;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.services.SoporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
     @Operation(summary = "Crear una nueva solicitud de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud creada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Soporte> crearSoporte(@Valid @RequestBody Soporte soporte) {
        Soporte nuevo = soporteService.crearSolicitud(soporte);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todas las solicitudes de soporte
    @Operation(summary = "Listar todas las solicitudes de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de solicitudes de soporte",
            content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<List<Soporte>> listarSoportes() {
        return ResponseEntity.ok(soporteService.obtenerTodas());
    }

    // Obtener solicitudes por estado (opcional)
    @Operation(summary = "Obtener solicitudes de soporte por estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitudes encontradas por estado",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "No se encontraron solicitudes con ese estado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Soporte>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(soporteService.obtenerPorEstado(estado));
    }

    // Cambiar el estado de una solicitud
    @Operation(summary = "Actualizar el estado de una solicitud de soporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Soporte.class))),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content),
        @ApiResponse(responseCode = "400", description = "Estado inválido o mal solicitado", content = @Content)
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<Soporte> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(soporteService.actualizarEstado(id, estado));
    }
}