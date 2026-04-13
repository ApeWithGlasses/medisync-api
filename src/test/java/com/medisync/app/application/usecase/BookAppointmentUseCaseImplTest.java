package com.medisync.app.application.usecase;

import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.AppointmentStatus;
import com.medisync.app.domain.model.Doctor;
import com.medisync.app.domain.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookAppointmentUseCaseImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private BookAppointmentUseCaseImpl useCase;

    @Test
    void shouldBookAppointmentWhenValid() {
        // Given
        Long patientId = 1L;
        Long doctorId = 2L;
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("John Doe");

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName("Dr. Smith");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findByDoctorIdAndDateTimeBetween(doctorId, dateTime.minusHours(1), dateTime.plusHours(1)))
                .thenReturn(List.of());
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment app = invocation.getArgument(0);
            app.setId(10L);
            return app;
        });

        // When
        Appointment result = useCase.bookAppointment(patientId, doctorId, dateTime);

        // Then
        assertThat(result.getPatientId()).isEqualTo(patientId);
        assertThat(result.getDoctorId()).isEqualTo(doctorId);
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
    }

    @Test
    void shouldThrowWhenPatientNotFound() {
        // Given
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.bookAppointment(1L, 2L, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Patient not found");
    }

    @Test
    void shouldThrowWhenDoctorNotFound() {
        // Given
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.bookAppointment(1L, 2L, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Doctor not found");
    }

    @Test
    void shouldThrowWhenDoctorHasConflictingAppointment() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Patient patient = new Patient();
        patient.setId(1L);
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        Appointment existing = new Appointment();
        existing.setId(5L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findByDoctorIdAndDateTimeBetween(2L, dateTime.minusHours(1), dateTime.plusHours(1)))
                .thenReturn(List.of(existing));

        // When & Then
        assertThatThrownBy(() -> useCase.bookAppointment(1L, 2L, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Doctor has a conflicting appointment");
    }
}
