package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Appointment;

import java.util.List;

public interface GetAppointmentsUseCase {
    List<Appointment> getAppointmentsByPatient(Long patientId);
    List<Appointment> getAppointmentsByDoctor(Long doctorId);
}
