package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.BookAppointmentUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.AppointmentStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BookAppointmentUseCaseImpl implements BookAppointmentUseCase {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime dateTime) {
        // Validate patient exists
        patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Validate doctor exists
        doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Check for conflicts: no overlapping appointments for the doctor
        List<Appointment> existing = appointmentRepository.findByDoctorIdAndDateTimeBetween(
                doctorId, dateTime.minusHours(1), dateTime.plusHours(1));
        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("Doctor has a conflicting appointment");
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return appointmentRepository.save(appointment);
    }
}
