package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetAllDoctorsUseCase;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.domain.model.Doctor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllDoctorsUseCaseImpl implements GetAllDoctorsUseCase {

    private final DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}

