package com.ecomarket.soporte.services;

import com.ecomarket.soporte.model.Soporte;
import com.ecomarket.soporte.repository.SoporteRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearSolicitud() {
        Soporte soporte = new Soporte();
        soporte.setNombreCliente("Cliente A");
        when(soporteRepository.save(any())).thenReturn(soporte);

        Soporte resultado = soporteService.crearSolicitud(soporte);
        assertEquals("Cliente A", resultado.getNombreCliente());
        verify(soporteRepository, times(1)).save(soporte);
    }

    @Test
    void testObtenerTodas() {
        List<Soporte> lista = List.of(new Soporte(), new Soporte());
        when(soporteRepository.findAll()).thenReturn(lista);

        List<Soporte> resultado = soporteService.obtenerTodas();
        assertEquals(2, resultado.size());
        verify(soporteRepository).findAll();
    }

    @Test
    void testObtenerPorEstado() {
        Soporte soporte = new Soporte();
        soporte.setEstado("pendiente");
        when(soporteRepository.findByEstado("pendiente")).thenReturn(List.of(soporte));

        List<Soporte> resultado = soporteService.obtenerPorEstado("pendiente");
        assertEquals(1, resultado.size());
        assertEquals("pendiente", resultado.get(0).getEstado());
        verify(soporteRepository).findByEstado("pendiente");
    }

    @Test
    void testActualizarEstado_OK() {
        Soporte soporte = new Soporte();
        soporte.setId(1L);
        soporte.setEstado("pendiente");

        when(soporteRepository.findById(1L)).thenReturn(Optional.of(soporte));
        when(soporteRepository.save(any())).thenReturn(soporte);

        Soporte actualizado = soporteService.actualizarEstado(1L, "resuelto");

        assertEquals("resuelto", actualizado.getEstado());
        verify(soporteRepository).findById(1L);
        verify(soporteRepository).save(soporte);
    }

    @Test
    void testActualizarEstado_NotFound() {
        when(soporteRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
            () -> soporteService.actualizarEstado(99L, "resuelto"));

        assertEquals("Solicitud de soporte no encontrada con ID: 99", ex.getMessage());
        verify(soporteRepository).findById(99L);
        verify(soporteRepository, never()).save(any());
    }
}
