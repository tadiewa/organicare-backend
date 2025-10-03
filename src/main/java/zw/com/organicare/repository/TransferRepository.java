/**
 * @author : tadiewa
 * date: 10/2/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Transfer;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer ,Long> {

    @Query("SELECT t.transferredFrom.accountId, SUM(t.amount) " +
            "FROM Transfer t WHERE t.createdOn BETWEEN :start AND :end " +
            "GROUP BY t.transferredFrom.accountId")
    List<Object[]> getTransfersOut(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT t.transferredTo.accountId, SUM(t.amount) " +
            "FROM Transfer t WHERE t.createdOn BETWEEN :start AND :end " +
            "GROUP BY t.transferredTo.accountId")
    List<Object[]> getTransfersIn(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
