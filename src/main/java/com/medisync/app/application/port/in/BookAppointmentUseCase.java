package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Appointment;

import java.time.LocalDateTime;

public interface BookAppointmentUseCase {
    Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime);
}
