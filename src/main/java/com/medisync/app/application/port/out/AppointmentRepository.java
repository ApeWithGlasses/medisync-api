package com.medisync.app.application.port.out;

import com.medisync.app.domain.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Optional<Appointment> findById(Long id);
    Appointment save(Appointment appointment);
    void deleteById(Long id);
    List<Appointment> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
}
