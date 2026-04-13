package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.domain.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientDataRepository extends CrudRepository<Patient, Long> {
}
