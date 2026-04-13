package com.medisync.app.infrastructure.config;

import com.medisync.app.application.port.in.BookAppointmentUseCase;
import com.medisync.app.application.port.in.CancelAppointmentUseCase;
import com.medisync.app.application.port.in.GetAppointmentsUseCase;
import com.medisync.app.application.port.out.AppointmentRepository;
import com.medisync.app.application.port.out.DoctorRepository;
import com.medisync.app.application.port.out.PatientRepository;
import com.medisync.app.application.usecase.BookAppointmentUseCaseImpl;
import com.medisync.app.application.usecase.CancelAppointmentUseCaseImpl;
import com.medisync.app.application.usecase.GetAppointmentsUseCaseImpl;
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
}
