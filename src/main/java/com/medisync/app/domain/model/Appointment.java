package com.medisync.app.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("APPOINTMENTS")
public class Appointment {
    @Id
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
}
