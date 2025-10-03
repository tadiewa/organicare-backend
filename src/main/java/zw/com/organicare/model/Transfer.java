/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;
import zw.com.organicare.constants.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transId;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private  Account transferredFrom;
    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private  Account transferredTo;
    private String  note ;
    @ManyToOne
    @JoinColumn(name = "receivedby_id")
    User receivedby ;
    @ManyToOne
    @JoinColumn(name = "requestedby_id")
    User requestedby ;
    @ManyToOne
    @JoinColumn(name = "authorizedby_id")
    User authorizedby ;
    String externalReceiverName;
    @Column(name ="id")
    String externalReceiverId;
    private  LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    private LocalDateTime authRejDate;
    private Boolean isAuthorized = false;



}
