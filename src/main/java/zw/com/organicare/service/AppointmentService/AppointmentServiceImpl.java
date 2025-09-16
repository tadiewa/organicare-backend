/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.Appointment.AppointmentDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Appointment;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.AppointmentRepository;
import zw.com.organicare.repository.PatientRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.utils.AppointmentMapper;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public AppointmentDto createAppointment(AppointmentDto dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        User createdBy = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Appointment appointment = AppointmentMapper.toEntity(dto, patient, doctor, createdBy);

        // Auto-generate appointment number if null
        if (appointment.getAppointmentNumber() == null) {
            appointment.setAppointmentNumber(generateAppointmentNumber());
        }

        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentMapper.toDto(saved);
    }

    public AppointmentDto getAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        return AppointmentMapper.toDto(appointment);
    }

    public AppointmentDto updateAppointment(Long id, AppointmentDto dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        appointment.setBranch(dto.getBranch());
        appointment.setWeight(dto.getWeight());
        appointment.setHeight(dto.getHeight());
        appointment.setIsApproved(dto.getIsApproved());
        appointment.setIsCompleted(dto.getIsCompleted());

        Appointment updated = appointmentRepository.save(appointment);
        return AppointmentMapper.toDto(updated);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        appointmentRepository.delete(appointment);
    }

    private String generateAppointmentNumber() {
        long count = appointmentRepository.count() + 1;
        return String.format("APT-%04d", count);
    }
}
