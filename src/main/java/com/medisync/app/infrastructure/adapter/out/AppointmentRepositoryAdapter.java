package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class AppointmentRepositoryAdapter implements AppointmentRepository {

    private final AppointmentDataRepository appointmentDataRepository;

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentDataRepository.findById(id);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentDataRepository.save(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentDataRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return appointmentDataRepository.findByDoctorIdAndDateTimeBetween(doctorId, start, end);
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        return appointmentDataRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        return appointmentDataRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findAll() {
        return StreamSupport.stream(appointmentDataRepository.findAll().spliterator(), false).toList();
    }
}
