package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Doctor;

import java.util.Optional;

public interface GetDoctorByIdUseCase {
    Optional<Doctor> getDoctorById(Long id);
}

