package com.ecomarket.soporte.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Entity
@Table(name = "soportes")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Entidad que representa una solicitud de soporte enviada por un cliente.")
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la solicitud", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres.")
    @Column(name = "nombre_cliente", nullable = false)
    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez", maxLength = 100)
    private String nombreCliente;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debe ser un correo válido.")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres.")
    @Column(name = "email_cliente", nullable = false)
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@example.com", maxLength = 100)
    private String emailCliente;

    @NotBlank(message = "El asunto es obligatorio.")
    @Size(max = 100, message = "El asunto no puede tener más de 100 caracteres.")
    @Schema(description = "Asunto del mensaje de soporte", example = "Problema con el pago", maxLength = 100)
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio.")
    @Size(max = 1000, message = "El mensaje no puede tener más de 1000 caracteres.")
    @Schema(description = "Cuerpo del mensaje enviado por el cliente", example = "Tuve un error al pagar mi pedido #12345", maxLength = 1000)
    private String mensaje;

    @Column(name = "fecha_creacion")
    @Schema(description = "Fecha y hora de creación de la solicitud", example = "2025-06-16T17:20:00")
    private LocalDateTime fechaCreacion;

    @NotBlank(message = "El estado de la solicitud es obligatorio.")
    @Size(max = 50, message = "El estado no puede superar los 50 caracteres.")
    @Column(nullable = false)
    @Schema(description = "Estado de la solicitud", example = "pendiente", allowableValues = {"pendiente", "en_proceso", "resuelto"}, maxLength = 50)
    private String estado;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "pendiente";
    }
}