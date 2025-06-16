package com.ecomarket.soporte;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.repository.SoporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SoporteIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SoporteRepository soporteRepository;

    @BeforeEach
    void setUp() {
        soporteRepository.deleteAll();
    }

    @Test
    void testCrearYObtenerSoporte() {
        Soporte soporte = new Soporte();
        soporte.setNombreCliente("Test Cliente");
        soporte.setEmailCliente("test@correo.cl");
        soporte.setAsunto("Test Asunto");
        soporte.setMensaje("Contenido del mensaje");

        ResponseEntity<Soporte> response = restTemplate.postForEntity(
                "/api/soportes",
                soporte,
                Soporte.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Cliente", response.getBody().getNombreCliente());

        List<Soporte> todos = soporteRepository.findAll();
        assertEquals(1, todos.size());
    }

    @Test
    void testActualizarEstadoYVerificar() {
        Soporte soporte = new Soporte();
        soporte.setNombreCliente("Estado Prueba");
        soporte.setEmailCliente("estado@mail.com");
        soporte.setAsunto("Asunto");
        soporte.setMensaje("Mensaje");
        soporte.setEstado("pendiente");
        soporte = soporteRepository.save(soporte);

        String url = "/api/soportes/" + soporte.getId() + "/estado?estado=resuelto";

        ResponseEntity<Soporte> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                Soporte.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("resuelto", response.getBody().getEstado());
    }

    @Test
    void testTiempoDeRespuesta() {
        long inicio = System.currentTimeMillis();

        ResponseEntity<String> response = restTemplate.getForEntity("/api/soportes", String.class);

        long duracion = System.currentTimeMillis() - inicio;

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(duracion < 500, "El tiempo de respuesta fue mayor a 500ms");
    }
}