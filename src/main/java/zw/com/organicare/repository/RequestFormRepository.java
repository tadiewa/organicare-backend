/**
 * @author : tadiewa
 * date: 9/15/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.RequestForm;

@Repository
public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
}
