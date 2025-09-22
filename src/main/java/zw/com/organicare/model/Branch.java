/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;
    private String branchName;
    private String branchLocation;
    private String branchCode;
    private boolean isActive;
}
