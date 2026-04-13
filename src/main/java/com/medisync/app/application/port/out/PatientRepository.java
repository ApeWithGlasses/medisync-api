package com.medisync.app.application.port.out;

import com.medisync.app.domain.model.Patient;

import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    void deleteById(Long id);
}
