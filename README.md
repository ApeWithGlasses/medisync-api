# Sistema de Gestión de Citas Médicas

Sistema backend para gestionar citas médicas. Implementado con Spring Boot 4.0.5, arquitectura hexagonal y pruebas unitarias.

## Requisitos

- Java 21 JDK
- Gradle 9.4.1 (incluido en el proyecto)

## Construcción

```bash
./gradlew build
```

Genera ejecutable en `build/libs/app-0.0.1-SNAPSHOT.jar`

## Ejecución

```bash
./gradlew bootRun
```

La aplicación inicia en `http://localhost:8080`

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/medisync/app/
│   │   ├── domain/
│   │   │   └── model/              # Entidades del dominio
│   │   ├── application/
│   │   │   ├── port/
│   │   │   │   ├── in/             # Puertos de entrada (casos de uso)
│   │   │   │   └── out/            # Puertos de salida (repositorios)
│   │   │   └── usecase/            # Implementaciones de casos de uso
│   │   └── infrastructure/
│   │       ├── adapter/
│   │       │   ├── in/             # Controladores REST
│   │       │   └── out/            # Adaptadores de persistencia
│   │       └── config/             # Configuración de inyección
│   └── resources/
│       ├── application.yaml        # Configuración de aplicación
│       └── schema.sql              # Script de base de datos
└── test/
    └── java/com/medisync/app/
        ├── application/usecase/    # Tests unitarios
        └── infrastructure/adapter/ # Tests de integración
```

## Arquitectura

Implementa arquitectura hexagonal (puertos y adaptadores) con principios SOLID.

**Capas:**

1. **Dominio**: Entidades y reglas de negocio
2. **Aplicación**: Casos de uso con puertos
3. **Infraestructura**: Controladores y repositorios

**Flujo de Solicitud:**

```
Cliente HTTP
    ↓
Controller (Adaptador de Entrada)
    ↓
UseCase (Lógica de Negocio)
    ↓
Repository (Adaptador de Salida)
    ↓
Base de Datos (H2)
```

## Base de Datos

H2 en memoria. Esquema inicializado automáticamente desde `schema.sql`.

Tablas:
- `patients`: Pacientes
- `doctors`: Médicos
- `appointments`: Citas

## API Endpoints

### Reservar Cita
```
POST /appointments
Content-Type: application/json

{
  "patientId": 1,
  "doctorId": 2,
  "dateTime": "2024-12-25T14:30:00"
}

Respuesta: 200 OK
{
  "id": 1,
  "patientId": 1,
  "doctorId": 2,
  "dateTime": "2024-12-25T14:30:00",
  "status": "SCHEDULED"
}
```

Validaciones:
- Paciente debe existir
- Médico debe existir
- Médico no puede tener cita en ese rango horario (±1 hora)

### Cancelar Cita
```
DELETE /appointments/{id}

Respuesta: 204 No Content
```

Validaciones:
- Cita debe existir
- Cita debe estar en estado SCHEDULED

### Obtener Citas por Paciente
```
GET /appointments/patient/{patientId}

Respuesta: 200 OK
[
  {
    "id": 1,
    "patientId": 1,
    "doctorId": 2,
    "dateTime": "2024-12-25T14:30:00",
    "status": "SCHEDULED"
  }
]
```

### Obtener Citas por Médico
```
GET /appointments/doctor/{doctorId}

Respuesta: 200 OK
[
  {
    "id": 1,
    "patientId": 1,
    "doctorId": 2,
    "dateTime": "2024-12-25T14:30:00",
    "status": "SCHEDULED"
  }
]
```

## Pruebas

Ejecutar pruebas:
```bash
./gradlew test
```

Tipos de pruebas:
- **Unitarias**: Casos de uso con mocks de repositorios
- **Integración**: Controladores con mocks de casos de uso

Cobertura: Se genera reporte JaCoCo

Ver reporte de cobertura:
```bash
./gradlew jacocoTestReport
open build/reports/jacoco/test/html/index.html
```

## Casos de Uso

### BookAppointmentUseCase
Reserva nueva cita. Valida existencia de paciente y médico. Verifica disponibilidad horaria del médico.

### CancelAppointmentUseCase
Cancela cita existente. Solo permite cancelar citas en estado SCHEDULED.

### GetAppointmentsUseCase
Obtiene listado de citas por paciente o médico.

## Modelos de Datos

### Patient
```
id: Long
name: String
email: String (único)
phone: String
```

### Doctor
```
id: Long
name: String
specialty: String
```

### Appointment
```
id: Long
patientId: Long (FK patients)
doctorId: Long (FK doctors)
dateTime: LocalDateTime
status: AppointmentStatus (SCHEDULED, COMPLETED, CANCELLED)
```

## Configuración

Archivo `application.yaml`:
```yaml
spring:
  application:
    name: app
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
```

## Dependencias Principales

- Spring Boot 4.0.5
- Spring Data JDBC
- H2 Database
- Lombok
- Mockito
- JaCoCo (cobertura)

## Datos Iniciales

La aplicación carga automáticamente 5 pacientes y 5 médicos al iniciar.

### Pacientes Disponibles

| ID | Nombre | Email | Teléfono |
|----|--------|-------|----------|
| 1 | Juan García | juan.garcia@email.com | 3001234567 |
| 2 | María López | maria.lopez@email.com | 3005678901 |
| 3 | Carlos Rodríguez | carlos.rodriguez@email.com | 3009876543 |
| 4 | Ana Martínez | ana.martinez@email.com | 3002468135 |
| 5 | Pedro Sánchez | pedro.sanchez@email.com | 3003691215 |

### Médicos Disponibles

| ID | Nombre | Especialidad |
|----|--------|--------------|
| 1 | Dr. Luis Pérez | Cardiología |
| 2 | Dra. Sofia Gómez | Dermatología |
| 3 | Dr. Miguel Torres | Cirugía General |
| 4 | Dra. Patricia Díaz | Oftalmología |
| 5 | Dr. Roberto Flores | Neurología |

### Ejemplos de Prueba

Reservar cita para Juan García (ID 1) con Dr. Luis Pérez (ID 1):

```bash
curl -X POST http://localhost:8080/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorId": 1,
    "dateTime": "2024-12-25T14:30:00"
  }'
```

Obtener citas del paciente Juan García:

```bash
curl http://localhost:8080/appointments/patient/1
```

Obtener citas del Dr. Luis Pérez:

```bash
curl http://localhost:8080/appointments/doctor/1
```

Cancelar cita (reemplazar ID con cita real):

```bash
curl -X DELETE http://localhost:8080/appointments/{appointmentId}
```
