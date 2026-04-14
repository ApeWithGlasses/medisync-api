package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.domain.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class PatientRepositoryAdapter implements PatientRepository {

    private final PatientDataRepository patientDataRepository;

    @Override
    public Optional<Patient> findById(Long id) {
        return patientDataRepository.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        return patientDataRepository.save(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientDataRepository.deleteById(id);
    }

    @Override
    public List<Patient> findAll() {
        return StreamSupport.stream(patientDataRepository.findAll().spliterator(), false).toList();
    }
}
