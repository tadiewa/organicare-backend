/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.Appointment.AppointmentDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.Appointment;
import zw.com.organicare.model.Branch;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.AppointmentRepository;
import zw.com.organicare.repository.BranchRepository;
import zw.com.organicare.repository.PatientRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.utils.AppointmentMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BranchRepository branchRepository;

    public AppointmentDto createAppointment(AppointmentDto dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new UserNotFound("Patient not found"));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new UserNotFound("Doctor not found"));

       // doctorappointmentCount(dto.getDoctorId());

        var currentUser = authService.getAuthenticatedUser();
       if(Boolean.FALSE==currentUser.getIsActive()){
              throw new ResourceNotFoundException("Inactive user cannot create appointment");
         }

        Appointment appointment = AppointmentMapper.toEntity(dto, patient, doctor, currentUser);

        // Auto-generate appointment number if null
        if (appointment.getAppointmentNumber() == null) {
            appointment.setAppointmentNumber(generateAppointmentNumber());
        }
        appointment.setBranch(currentUser.getBranch());
        appointment.setAppointmentDate(LocalDateTime.now());
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

        Appointment updatedAppointment = Appointment.builder()
                .appointmentId(appointment.getAppointmentId())
                .branch(appointment.getBranch())
                .patient(appointment.getPatient())
                .appointmentDate(appointment.getAppointmentDate())
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .isApproved(appointment.getIsApproved())
                .isCompleted(appointment.getIsCompleted())
                .build();

        Appointment saved = appointmentRepository.save(updatedAppointment);
        return AppointmentMapper.toDto(saved);
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


    private int doctorappointmentCount(long doctorId){

        var appointments = appointmentRepository.findByDoctor_UserId(doctorId);
        return appointments.size();

}

    @Override
    public AppointmentDto docUpdateAppointment(Long id, AppointmentDto dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Appointment updatedAppointment = Appointment.builder()
                .appointmentId(appointment.getAppointmentId())
                .branch(appointment.getBranch())
                .patient(appointment.getPatient())
                .appointmentDate(appointment.getAppointmentDate())
                .weight(appointment.getWeight())
                .height(appointment.getHeight())
                .isApproved(dto.getIsApproved() != null ? dto.getIsApproved() : appointment.getIsApproved())
                .isCompleted(dto.getIsCompleted() != null ? dto.getIsCompleted() : appointment.getIsCompleted())
                .build();

        Appointment saved = appointmentRepository.save(updatedAppointment);
        return AppointmentMapper.toDto(saved);
    }

}
