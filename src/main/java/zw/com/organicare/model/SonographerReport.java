/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investigation_reports")
public class SonographerReport {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long reportId;

        private String findings;
        private String impressions;
        private String sonographerNotes;
        private Date reportDate;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "request_form_id", nullable = false)
        private RequestForm requestForm;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "created_by")
        private User createdBy;

}
