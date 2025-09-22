/**
 * @author : tadiewa
 * date: 9/22/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
