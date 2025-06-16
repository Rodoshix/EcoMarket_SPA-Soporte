package com.ecomarket.soporte.controller;

import com.ecomarket.soporte.assembler.SoporteModelAssembler;
import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.services.SoporteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/soportes")
public class SoporteControllerV2 {

    @Autowired
    private SoporteService soporteService;

    @Autowired
    private SoporteModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Soporte>>> listarSoportes() {
        List<Soporte> soportes = soporteService.obtenerTodas();
        List<EntityModel<Soporte>> modelos = soportes.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(modelos,
                        linkTo(methodOn(SoporteControllerV2.class).listarSoportes()).withSelfRel())
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel<Soporte>> crearSoporte(@RequestBody Soporte soporte) {
        Soporte nuevo = soporteService.crearSolicitud(soporte);
        return ResponseEntity.ok(assembler.toModel(nuevo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<CollectionModel<EntityModel<Soporte>>> obtenerPorEstado(@PathVariable String estado) {
        List<Soporte> soportes = soporteService.obtenerPorEstado(estado);
        List<EntityModel<Soporte>> modelos = soportes.stream()
                .map(assembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(modelos,
                        linkTo(methodOn(SoporteControllerV2.class).obtenerPorEstado(estado)).withSelfRel())
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Soporte>> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        Soporte actualizado = soporteService.actualizarEstado(id, estado);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }
}