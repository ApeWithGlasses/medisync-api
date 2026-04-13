package com.medisync.app.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("DOCTORS")
public class Doctor {
    @Id
    private Long id;
    private String name;
    private String specialty;
}
