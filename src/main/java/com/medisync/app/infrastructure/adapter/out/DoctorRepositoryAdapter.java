package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.domain.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class DoctorRepositoryAdapter implements DoctorRepository {

    private final DoctorDataRepository doctorDataRepository;

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorDataRepository.findById(id);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorDataRepository.save(doctor);
    }

    @Override
    public void deleteById(Long id) {
        doctorDataRepository.deleteById(id);
    }

    @Override
    public List<Doctor> findAll() {
        return StreamSupport.stream(doctorDataRepository.findAll().spliterator(), false).toList();
    }
}
