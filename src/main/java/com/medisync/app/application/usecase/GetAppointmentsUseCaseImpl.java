package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetAppointmentsUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAppointmentsUseCaseImpl implements GetAppointmentsUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
