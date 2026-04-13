package com.medisync.app.application.usecase;

import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentsUseCaseImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private GetAppointmentsUseCaseImpl useCase;

    @Test
    void shouldGetAppointmentsByPatient() {
        // Given
        Long patientId = 1L;
        List<Appointment> appointments = List.of(new Appointment());
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(appointments);

        // When
        List<Appointment> result = useCase.getAppointmentsByPatient(patientId);

        // Then
        assertThat(result).isEqualTo(appointments);
    }

    @Test
    void shouldGetAppointmentsByDoctor() {
        // Given
        Long doctorId = 2L;
        List<Appointment> appointments = List.of(new Appointment());
        when(appointmentRepository.findByDoctorId(doctorId)).thenReturn(appointments);

        // When
        List<Appointment> result = useCase.getAppointmentsByDoctor(doctorId);

        // Then
        assertThat(result).isEqualTo(appointments);
    }
}
