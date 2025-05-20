package com.ecomarket.soporte.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "soportes")
@Getter
@Setter
@NoArgsConstructor
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo válido.")
    @Column(name = "email_cliente", nullable = false)
    private String emailCliente;

    @NotBlank(message = "El asunto es obligatorio.")
    @Size(max = 100, message = "El asunto no puede tener más de 100 caracteres.")
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio.")
    @Size(max = 1000, message = "El mensaje no puede tener más de 1000 caracteres.")
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private String estado;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "pendiente";
    }
}