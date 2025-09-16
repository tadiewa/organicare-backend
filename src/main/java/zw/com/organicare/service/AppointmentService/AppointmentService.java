/**
 * @author : tadiewa
 * date: 9/15/2025
 */

package zw.com.organicare.service.AppointmentService;

import zw.com.organicare.dto.Appointment.AppointmentDto;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto dto);
    AppointmentDto getAppointment(Long id);
    AppointmentDto updateAppointment(Long id, AppointmentDto dto);
    void deleteAppointment(Long id);
}
