package com.medisync.app.infrastructure.adapter.in;

import com.medisync.app.application.port.in.*;
import com.medisync.app.domain.model.Appointment;
import com.medisync.app.domain.model.Doctor;
import com.medisync.app.domain.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppointmentController {

    private final BookAppointmentUseCase bookAppointmentUseCase;
    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final GetAppointmentsUseCase getAppointmentsUseCase;
    private final GetAllDoctorsUseCase getAllDoctorsUseCase;
    private final GetAllPatientsUseCase getAllPatientsUseCase;
    private final GetAllAppointmentsUseCase getAllAppointmentsUseCase;
    private final GetAppointmentByIdUseCase getAppointmentByIdUseCase;
    private final GetDoctorByIdUseCase getDoctorByIdUseCase;
    private final GetPatientByIdUseCase getPatientByIdUseCase;

    @PostMapping("/appointments")
    public ResponseEntity<Appointment> bookAppointment(@RequestBody BookAppointmentRequest request) {
        Appointment appointment = bookAppointmentUseCase.bookAppointment(
                request.getPatientId(), request.getDoctorId(), request.getDateTime());
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        cancelAppointmentUseCase.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointments/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = getAppointmentsUseCase.getAppointmentsByPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointments/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = getAppointmentsUseCase.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = getAllDoctorsUseCase.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return getDoctorByIdUseCase.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = getAllPatientsUseCase.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return getPatientByIdUseCase.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = getAllAppointmentsUseCase.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return getAppointmentByIdUseCase.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
