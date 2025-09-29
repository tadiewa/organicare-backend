/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.com.organicare.dto.sale.DailyAgentSalesDto;
import zw.com.organicare.dto.sale.DailyRpSalesDto;
import zw.com.organicare.model.Sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query("""
       SELECT new zw.com.organicare.dto.sale.DailyAgentSalesDto(
           s.agent.userId,
           s.agent.fullName,
           SUM(s.totalAmountDue)
       )
       FROM Sale s
       WHERE DATE(s.saleDate) = :date
       GROUP BY s.agent.userId, s.agent.fullName
       """)
    List<DailyAgentSalesDto> getDailyTotalsByAgent(@Param("date") LocalDate date);



    @Query("""
       SELECT new zw.com.organicare.dto.sale.DailyRpSalesDto(
           p.referringPractitioner.rp_Id,
           p.referringPractitioner.user.fullName,
           SUM(s.totalAmountDue)
       )
       FROM Sale s
       JOIN s.patient p
       WHERE DATE(s.saleDate) = :date
       GROUP BY p.referringPractitioner.rp_Id, p.referringPractitioner.user.fullName
       """)
    List<DailyRpSalesDto> getDailyTotalsByRp(@Param("date") LocalDate date);

    @Query("""
    SELECT DISTINCT s
    FROM Sale s
    JOIN FETCH s.saleLines sl
    JOIN FETCH sl.product p
    JOIN s.patient pat
    JOIN pat.referringPractitioner rp
    WHERE s.saleDate BETWEEN :startDate AND :endDate
      AND rp.user.fullName = :rpName
    ORDER BY s.saleDate
""")
    List<Sale> findSalesWithProductsByRpAndDateRange(
            @Param("rpName") String rpName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );




}
