/**
 * @author : tadiewa
 * date: 10/2/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByAccountName(String name);
}
