package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Patient;

import java.util.Optional;

public interface GetPatientByIdUseCase {
    Optional<Patient> getPatientById(Long id);
}

