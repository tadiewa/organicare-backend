/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.patientCard.SonographerReportDto;
import zw.com.organicare.model.RequestForm;
import zw.com.organicare.model.SonographerReport;
import zw.com.organicare.model.User;

import java.util.Date;

//public class SonographerReportMapper {
//
//    // Convert entity to DTO
//    public static SonographerReportDto toDto(SonographerReport report) {
//        if (report == null) return null;
//
//        return SonographerReportDto.builder()
//                .reportId(report.getReportId())
//                .findings(report.getFindings())
//                .impressions(report.getImpressions())
//                .sonographerNotes(report.getSonographerNotes())
//                .reportDate(report.getReportDate())
//                .requestFormId(report.getRequestForm() != null ? report.getRequestForm().getRequestFormId() : null)
//                .performedById(report.getCreatedBy() != null ? report.getCreatedBy().getUserId() : null)
//                .build();
//    }
//
//    // Convert DTO to entity
//    public static SonographerReport toEntity(SonographerReportDto dto, RequestForm requestForm, User sonographer) {
//        if (dto == null) return null;
//
//        SonographerReport report = new SonographerReport();
//        report.setReportId(dto.getReportId());
//        report.setFindings(dto.getFindings());
//        report.setImpressions(dto.getImpressions());
//        report.setSonographerNotes(dto.getSonographerNotes());
//        report.setReportDate(dto.getReportDate() != null ? dto.getReportDate() : new Date());
//        report.setRequestForm(requestForm);
//        report.setCreatedBy(sonographer);
//
//        return report;
//    }
//}

public class SonographerReportMapper {

    public static SonographerReportDto toDto(SonographerReport report) {
        if (report == null) return null;

        return SonographerReportDto.builder()
                .reportId(report.getReportId())
                .findings(report.getFindings())
                .impressions(report.getImpressions())
                .sonographerNotes(report.getSonographerNotes())
                .reportDate(report.getReportDate())
                .requestFormId(report.getRequestForm() != null ? report.getRequestForm().getRequestFormId() : null)
                .performedById(report.getCreatedBy() != null ? report.getCreatedBy().getUserId() : null)
                //.createdByName(report.getCreatedBy() != null ? report.getCreatedBy().getFullName() : null)
                .build();
    }

    public static SonographerReport toEntity(SonographerReportDto dto, RequestForm requestForm, User creator) {
        if (dto == null) return null;

        SonographerReport report = new SonographerReport();
        report.setReportId(dto.getReportId());
        report.setFindings(dto.getFindings());
        report.setImpressions(dto.getImpressions());
        report.setSonographerNotes(dto.getSonographerNotes());
        report.setReportDate(dto.getReportDate() != null ? dto.getReportDate() : new Date());
        report.setRequestForm(requestForm);
        report.setCreatedBy(creator.getFullName() != null ? creator : null);

        return report;
    }

}
