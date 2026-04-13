package com.medisync.app.infrastructure.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medisync.app.application.port.in.BookAppointmentUseCase;
import com.medisync.app.application.port.in.CancelAppointmentUseCase;
import com.medisync.app.application.port.in.GetAppointmentsUseCase;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.AppointmentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private BookAppointmentUseCase bookAppointmentUseCase;

    @Mock
    private CancelAppointmentUseCase cancelAppointmentUseCase;

    @Mock
    private GetAppointmentsUseCase getAppointmentsUseCase;

    @InjectMocks
    private AppointmentController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void shouldBookAppointment() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId(1L);
        appointment.setDoctorId(2L);
        appointment.setDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        when(bookAppointmentUseCase.bookAppointment(1L, 2L, dateTime)).thenReturn(appointment);

        AppointmentController.BookAppointmentRequest request = new AppointmentController.BookAppointmentRequest(1L, 2L, dateTime);

        // When & Then
        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.patientId").value(1))
                .andExpect(jsonPath("$.doctorId").value(2));
    }

    @Test
    void shouldCancelAppointment() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        doNothing().when(cancelAppointmentUseCase).cancelAppointment(1L);

        // When & Then
        mockMvc.perform(delete("/appointments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAppointmentsByPatient() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatientId(1L);
        when(getAppointmentsUseCase.getAppointmentsByPatient(1L)).thenReturn(List.of(appointment));

        // When & Then
        mockMvc.perform(get("/appointments/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldGetAppointmentsByDoctor() throws Exception {
        // Given
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDoctorId(2L);
        when(getAppointmentsUseCase.getAppointmentsByDoctor(2L)).thenReturn(List.of(appointment));

        // When & Then
        mockMvc.perform(get("/appointments/doctor/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
