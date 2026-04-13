package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.domain.model.Appointment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentDataRepository extends CrudRepository<Appointment, Long> {

    @Query("SELECT * FROM appointments WHERE patient_id = :patientId")
    List<Appointment> findByPatientId(Long patientId);

    @Query("SELECT * FROM appointments WHERE doctor_id = :doctorId")
    List<Appointment> findByDoctorId(Long doctorId);

    @Query("SELECT * FROM appointments WHERE doctor_id = :doctorId AND date_time BETWEEN :start AND :end")
    List<Appointment> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
}
