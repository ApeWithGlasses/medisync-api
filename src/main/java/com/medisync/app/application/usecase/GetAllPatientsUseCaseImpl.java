package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetAllPatientsUseCase;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.domain.model.Patient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllPatientsUseCaseImpl implements GetAllPatientsUseCase {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}

