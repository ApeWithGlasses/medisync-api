package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Patient;

import java.util.List;

public interface GetAllPatientsUseCase {
    List<Patient> getAllPatients();
}

