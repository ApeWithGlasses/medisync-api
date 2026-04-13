package com.medisync.app.application.usecase;

import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.AppointmentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private CancelAppointmentUseCaseImpl useCase;

    @Test
    void shouldCancelAppointmentWhenScheduled() {
        // Given
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setId(appointmentId);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // When
        useCase.cancelAppointment(appointmentId);

        // Then
        verify(appointmentRepository).save(appointment);
        assert appointment.getStatus() == AppointmentStatus.CANCELLED;
    }

    @Test
    void shouldThrowWhenAppointmentNotFound() {
        // Given
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.cancelAppointment(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Appointment not found");
    }

    @Test
    void shouldThrowWhenAppointmentNotScheduled() {
        // Given
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(AppointmentStatus.COMPLETED);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        // When & Then
        assertThatThrownBy(() -> useCase.cancelAppointment(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot cancel appointment that is not scheduled");
    }
}
