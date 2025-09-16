/**
 * @author : tadiewa
 * date: 7/23/2025
 */

package zw.com.organicare.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.com.organicare.constants.Role;
import zw.com.organicare.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Page<User> findByRole(Role role, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
}
