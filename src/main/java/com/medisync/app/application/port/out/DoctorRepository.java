package com.medisync.app.application.port.out;

import com.medisync.app.domain.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Optional<Doctor> findById(Long id);
    Doctor save(Doctor doctor);
    void deleteById(Long id);
    List<Doctor> findAll();
}
