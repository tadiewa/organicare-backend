/**
 * @author : tadiewa
 * date: 10/1/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
