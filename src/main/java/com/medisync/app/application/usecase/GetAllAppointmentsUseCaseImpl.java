package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetAllAppointmentsUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllAppointmentsUseCaseImpl implements GetAllAppointmentsUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}

