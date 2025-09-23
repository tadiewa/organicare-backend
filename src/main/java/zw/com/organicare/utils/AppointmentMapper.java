/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import org.springframework.stereotype.Component;
import zw.com.organicare.dto.Appointment.AppointmentDto;
import zw.com.organicare.model.Appointment;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.User;

@Component
public class AppointmentMapper {

    public static AppointmentDto toDto(Appointment appointment) {
        if (appointment == null) return null;

        return AppointmentDto.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentNumber(appointment.getAppointmentNumber())
                .patientId(appointment.getPatient() != null ? appointment.getPatient().getPatientId() : null)
                .doctorId(appointment.getDoctor() != null ? appointment.getDoctor().getUserId() : null)
                .weight(appointment.getWeight())
                .height(appointment.getHeight())
                .branchId(appointment.getAppointmentId())
                .isApproved(appointment.getIsApproved())
                .isCompleted(appointment.getIsCompleted())
                .createdById(appointment.getCreatedBy() != null ? appointment.getCreatedBy().getUserId() : null)
                .build();
    }

    public static Appointment toEntity(AppointmentDto dto, Patient patient, User doctor, User createdBy) {
        return Appointment.builder()
                .appointmentId(dto.getAppointmentId())
                .appointmentNumber(dto.getAppointmentNumber())
                .patient(patient)
                .doctor(doctor)
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .isApproved(dto.getIsApproved())
                .isCompleted(dto.getIsCompleted())
                .createdBy(createdBy)
                .build();
    }
}

