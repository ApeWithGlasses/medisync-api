package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetPatientByIdUseCase;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.domain.model.Patient;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetPatientByIdUseCaseImpl implements GetPatientByIdUseCase {

    private final PatientRepository patientRepository;

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
}

