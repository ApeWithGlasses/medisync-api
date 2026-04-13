package com.medisync.app.infrastructure.adapter.out;

import com.medisync.app.domain.model.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorDataRepository extends CrudRepository<Doctor, Long> {
}
