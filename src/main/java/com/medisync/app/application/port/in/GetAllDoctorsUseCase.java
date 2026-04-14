package com.medisync.app.application.port.in;

import com.medisync.app.domain.model.Doctor;

import java.util.List;

public interface GetAllDoctorsUseCase {
    List<Doctor> getAllDoctors();
}

