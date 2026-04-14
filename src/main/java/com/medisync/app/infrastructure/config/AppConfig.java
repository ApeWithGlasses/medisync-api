package com.medisync.app.infrastructure.config;

import com.medisync.app.application.port.in.*;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.application.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BookAppointmentUseCase bookAppointmentUseCase(PatientRepository patientRepository,
                                                          DoctorRepository doctorRepository,
                                                          AppointmentRepository appointmentRepository) {
        return new BookAppointmentUseCaseImpl(patientRepository, doctorRepository, appointmentRepository);
    }

    @Bean
    public CancelAppointmentUseCase cancelAppointmentUseCase(AppointmentRepository appointmentRepository) {
        return new CancelAppointmentUseCaseImpl(appointmentRepository);
    }

    @Bean
    public GetAppointmentsUseCase getAppointmentsUseCase(AppointmentRepository appointmentRepository) {
        return new GetAppointmentsUseCaseImpl(appointmentRepository);
    }

    @Bean
    public GetAllDoctorsUseCase getAllDoctorsUseCase(DoctorRepository doctorRepository) {
        return new GetAllDoctorsUseCaseImpl(doctorRepository);
    }

    @Bean
    public GetAllPatientsUseCase getAllPatientsUseCase(PatientRepository patientRepository) {
        return new GetAllPatientsUseCaseImpl(patientRepository);
    }

    @Bean
    public GetAllAppointmentsUseCase getAllAppointmentsUseCase(AppointmentRepository appointmentRepository) {
        return new GetAllAppointmentsUseCaseImpl(appointmentRepository);
    }

    @Bean
    public GetAppointmentByIdUseCase getAppointmentByIdUseCase(AppointmentRepository appointmentRepository) {
        return new GetAppointmentByIdUseCaseImpl(appointmentRepository);
    }

    @Bean
    public GetDoctorByIdUseCase getDoctorByIdUseCase(DoctorRepository doctorRepository) {
        return new GetDoctorByIdUseCaseImpl(doctorRepository);
    }

    @Bean
    public GetPatientByIdUseCase getPatientByIdUseCase(PatientRepository patientRepository) {
        return new GetPatientByIdUseCaseImpl(patientRepository);
    }
}
