package com.ecomarket.soporte.controller;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.services.SoporteService;

import org.springframework.hateoas.Link;

import com.ecomarket.soporte.assembler.SoporteModelAssembler;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SoporteControllerV2.class)
@ActiveProfiles("test")
public class SoporteControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoporteService soporteService;

    @MockBean
    private SoporteModelAssembler soporteModelAssembler;

    @Test
    void testListarSoportesConHATEOAS() throws Exception {
        Soporte soporte = new Soporte();
        soporte.setId(1L);
        soporte.setNombreCliente("Rodrigo");
        soporte.setEmailCliente("rodrigo@mail.cl");
        soporte.setAsunto("Consulta");
        soporte.setMensaje("¿Dónde está mi pedido?");
        soporte.setEstado("pendiente");

        EntityModel<Soporte> soporteModel = EntityModel.of(soporte,
            Link.of("http://localhost/api/v2/soportes", "todos"),
            Link.of("http://localhost/api/v2/soportes/estado/pendiente", "por-estado"),
            Link.of("http://localhost/api/v2/soportes/1/estado?estado=resuelto", "marcar-resuelto")
        );
        Mockito.when(soporteService.obtenerTodas()).thenReturn(List.of(soporte));
        Mockito.when(soporteModelAssembler.toModel(any())).thenReturn(soporteModel);

        mockMvc.perform(get("/api/v2/soportes")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.soporteList").exists())
            .andExpect(jsonPath("$._embedded.soporteList[0].nombreCliente", is("Rodrigo")))
            .andExpect(jsonPath("$._embedded.soporteList[0]._links.por-estado").exists())
            .andExpect(jsonPath("$._embedded.soporteList[0]._links.marcar-resuelto").exists());
    }
}
