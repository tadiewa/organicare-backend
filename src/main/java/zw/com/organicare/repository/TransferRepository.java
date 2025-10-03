/**
 * @author : tadiewa
 * date: 10/2/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer ,Long> {
}
