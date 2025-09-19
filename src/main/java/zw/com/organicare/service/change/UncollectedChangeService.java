/**
 * @author : tadiewa
 * date: 9/19/2025
 */

package zw.com.organicare.service.change;

import zw.com.organicare.dto.change.UncollectedChangeResponse;
import zw.com.organicare.model.UncollectedChange;

import java.util.List;

public interface UncollectedChangeService {
    UncollectedChangeResponse collectChange(Long uncollectedChangeId);
    List<UncollectedChangeResponse> getPendingChangesForPatient(Long patientId);
}
