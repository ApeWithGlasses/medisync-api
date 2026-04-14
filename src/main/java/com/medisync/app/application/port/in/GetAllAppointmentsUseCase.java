package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Appointment;

import java.util.List;

public interface GetAllAppointmentsUseCase {
    List<Appointment> getAllAppointments();
}

