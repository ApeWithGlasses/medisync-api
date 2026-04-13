package com.medisync.app.infrastructure.adapter.in;

import com.medisync.app.application.port.in.BookAppointmentUseCase;
import com.medisync.app.application.port.in.CancelAppointmentUseCase;
import com.medisync.app.application.port.in.GetAppointmentsUseCase;
import com.medisync.app.domain.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final BookAppointmentUseCase bookAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final GetAppointmentsUseCase getAppointmentsUseCase;

    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(@RequestBody BookAppointmentRequest request) {
        Appointment appointment = bookAppointmentUseCase.bookAppointment(
                request.getPatientId(), request.getDoctorId(), request.getDateTime());
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        cancelAppointmentUseCase.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = getAppointmentsUseCase.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = getAppointmentsUseCase.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }

    public static class BookAppointmentRequest {
        private Long patientId;
        private Long doctorId;
        private LocalDateTime dateTime;

        public BookAppointmentRequest() {}

        public BookAppointmentRequest(Long patientId, Long doctorId, LocalDateTime dateTime) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.dateTime = dateTime;
        }

        public Long getPatientId() { return patientId; }
        public Long getDoctorId() { return doctorId; }
        public LocalDateTime getDateTime() { return dateTime; }

        public void setPatientId(Long patientId) { this.patientId = patientId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
        public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    }
}
