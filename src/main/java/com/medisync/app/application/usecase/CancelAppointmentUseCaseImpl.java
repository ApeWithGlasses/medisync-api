package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.CancelAppointmentUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.AppointmentStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CancelAppointmentUseCaseImpl implements CancelAppointmentUseCase {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalStateException("Cannot cancel appointment that is not scheduled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
