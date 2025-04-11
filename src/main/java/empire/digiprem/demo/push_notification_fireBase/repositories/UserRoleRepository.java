package empire.digiprem.demo.push_notification_fireBase.repositories;

import empire.digiprem.demo.push_notification_fireBase.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findRoleByName(String name);
}
