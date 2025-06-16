package com.ecomarket.soporte.controller;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.services.SoporteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SoporteController.class)
@ActiveProfiles("test")
class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoporteService soporteService;

    @Test
    void testObtenerTodasLasSolicitudes() throws Exception {
        Soporte s1 = new Soporte();
        s1.setId(1L);
        s1.setNombreCliente("Carlos Soto");
        s1.setEmailCliente("carlos@mail.com");
        s1.setAsunto("Retraso en entrega");
        s1.setMensaje("Mi pedido no ha llegado.");
        s1.setEstado("pendiente");

        Mockito.when(soporteService.obtenerTodas()).thenReturn(List.of(s1));

        mockMvc.perform(get("/api/soportes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nombreCliente", is("Carlos Soto")));
    }

    @Test
    void testCrearSoporte() throws Exception {
        Soporte soporte = new Soporte();
        soporte.setId(2L);
        soporte.setNombreCliente("Ana");
        soporte.setEmailCliente("ana@mail.com");
        soporte.setAsunto("Error");
        soporte.setMensaje("Tengo un problema");
        soporte.setEstado("pendiente");

        Mockito.when(soporteService.crearSolicitud(any(Soporte.class))).thenReturn(soporte);

        String json = "{"
        + "\"nombreCliente\": \"Ana\","
        + "\"emailCliente\": \"ana@mail.com\","
        + "\"asunto\": \"Error\","
        + "\"mensaje\": \"Tengo un problema\""
        + "}";

        mockMvc.perform(post("/api/soportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCliente", is("Ana")));
    }

    @Test
    void testObtenerPorEstado() throws Exception {
        Soporte soporte = new Soporte();
        soporte.setId(3L);
        soporte.setNombreCliente("Luis");
        soporte.setEstado("resuelto");

        Mockito.when(soporteService.obtenerPorEstado("resuelto")).thenReturn(List.of(soporte));

        mockMvc.perform(get("/api/soportes/estado/resuelto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCliente", is("Luis")));
    }

    @Test
    void testActualizarEstado() throws Exception {
        Soporte soporte = new Soporte();
        soporte.setId(4L);
        soporte.setEstado("en_proceso");

        Mockito.when(soporteService.actualizarEstado(eq(4L), eq("en_proceso"))).thenReturn(soporte);

        mockMvc.perform(put("/api/soportes/4/estado?estado=en_proceso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("en_proceso")));
    }
}