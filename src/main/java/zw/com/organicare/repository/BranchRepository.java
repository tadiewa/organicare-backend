/**
 * @author : tadiewa
 * date: 9/22/2025
 */

package zw.com.organicare.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

   // Page<Branch> getAllBranch(int page , int size);
}
