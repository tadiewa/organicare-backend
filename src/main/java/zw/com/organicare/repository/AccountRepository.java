/**
 * @author : tadiewa
 * date: 10/2/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

   Optional<Account> findAccountByAccountName(String name);
    @Query("""
        SELECT COALESCE(a.accountBalance, 0)
        FROM Account a
        WHERE a.accountName = :accountName
          AND a.closingDate <= :date
        ORDER BY a.closingDate DESC
    """)
    List<BigDecimal> getOpeningBalance(@Param("accountName") String accountName,
                                       @Param("date") LocalDate date);
}
