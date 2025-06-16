package com.ecomarket.soporte.assembler;

import com.ecomarket.soporte.controller.SoporteControllerV2;
import com.ecomarket.soporte.model.Soporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Soporte, EntityModel<Soporte>> {

    @Override
    public EntityModel<Soporte> toModel(Soporte soporte) {
        return EntityModel.of(soporte,
                linkTo(methodOn(SoporteControllerV2.class).listarSoportes()).withRel("todos"),
                linkTo(methodOn(SoporteControllerV2.class).obtenerPorEstado(soporte.getEstado())).withRel("por-estado"),
                linkTo(methodOn(SoporteControllerV2.class).actualizarEstado(soporte.getId(), "resuelto")).withRel("marcar-resuelto")
        );
    }
}