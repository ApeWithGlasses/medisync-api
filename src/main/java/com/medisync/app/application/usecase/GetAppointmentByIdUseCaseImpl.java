package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetAppointmentByIdUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetAppointmentByIdUseCaseImpl implements GetAppointmentByIdUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
}

