# EcoMarket SPA - Microservicio Soporte-Service

Este microservicio gestiona las solicitudes de soporte realizadas por los clientes desde la plataforma web de EcoMarket SPA. Forma parte del proyecto semestral **FullStack_1**, permitiendo registrar consultas, reclamos o incidencias, y darles seguimiento desde el área administrativa.

---

## Tecnologías

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Lombok
- MySQL
- Jakarta Validation

---

## Configuración del entorno

### Base de datos

- Motor: MySQL (MariaDB compatible)
- Nombre: `ecomarket_soporte`
- Usuario: `root`
- Contraseña: *(vacía por defecto en XAMPP)*

### Archivo `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecomarket_soporte?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8088
```

---

## Funcionalidades

- Registrar solicitudes de soporte con datos del cliente y mensaje.
- Consultar todas las solicitudes registradas.
- Filtrar solicitudes por estado: `pendiente`, `en proceso`, `resuelto`.
- Actualizar el estado de una solicitud para dar seguimiento interno.
- Validación de entradas con `@Valid` y manejo básico de errores.

---

## Endpoints disponibles

Importa la colección incluida:  
**`EcoMarket - Soporte-Service.postman_collection.json`**

| Método | Endpoint                                      | Descripción                                                              |
|--------|-----------------------------------------------|--------------------------------------------------------------------------|
| POST   | `/api/soportes`                               | Crea una nueva solicitud de soporte.                                     |
| GET    | `/api/soportes`                               | Lista todas las solicitudes registradas.                                 |
| GET    | `/api/soportes/estado/{estado}`               | Obtiene solicitudes filtradas por estado.                                |
| PUT    | `/api/soportes/{id}/estado?estado=nuevoEstado`| Actualiza el estado de una solicitud (ej: a 'resuelto' o 'en proceso').  |

---

## Ejemplo de uso en Postman

Importa la colección incluida:  
**`soporte-service.postman_collection.json`**

### Crear solicitud
```http
POST http://localhost:8087/api/soportes
Content-Type: application/json

{
  "nombreCliente": "Cliente",
  "emailCliente": "cliente@example.com",
  "asunto": "Problema con el carrito",
  "mensaje": "No se agregan productos al carrito desde Firefox."
}
```

### Cambiar estado de solicitud
```http
PUT http://localhost:8088/api/soportes/1/estado?estado=resuelto
```

---

## Microservicios relacionados

Actualmente no depende de otros microservicios, pero podría integrarse con:

| Servicio                 | Función potencial                                     |
|--------------------------|-------------------------------------------------------|
| `usuarios-auth-service`  | Validar autenticación de clientes o administradores.  |
| `notificacion-service`   | Enviar correos automáticos al recibir solicitudes.    |

---
