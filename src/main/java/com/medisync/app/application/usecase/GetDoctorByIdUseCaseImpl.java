package com.medisync.app.application.usecase;

import com.medisync.app.application.port.in.GetDoctorByIdUseCase;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.domain.model.Doctor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetDoctorByIdUseCaseImpl implements GetDoctorByIdUseCase {

    private final DoctorRepository doctorRepository;

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
}

