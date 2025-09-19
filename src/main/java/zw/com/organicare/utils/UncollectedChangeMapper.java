/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.change.UncollectedChangeResponse;
import zw.com.organicare.model.UncollectedChange;

public class UncollectedChangeMapper {
    public static UncollectedChangeResponse toDto(UncollectedChange entity) {
        UncollectedChangeResponse dto = new UncollectedChangeResponse();
        dto.setId(entity.getUncollectedChangeId());
        dto.setChangeAmount(entity.getChangeAmount());
        dto.setResolved(entity.isResolved());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setNotes(entity.getNotes());

        if (entity.getPatient() != null) {
            dto.setPatientName(entity.getPatient().getFullName());
        }
        if (entity.getSalesAgent() != null) {
            dto.setSalesAgentName(entity.getSalesAgent().getFullName());
        }
        return dto;
    }
}

