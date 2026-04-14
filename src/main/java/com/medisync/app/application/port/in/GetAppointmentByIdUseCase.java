package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Appointment;

import java.util.Optional;

public interface GetAppointmentByIdUseCase {
    Optional<Appointment> getAppointmentById(Long id);
}

