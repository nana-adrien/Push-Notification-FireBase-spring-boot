package empire.digiprem.demo.push_notification_fireBase.repositories;

import empire.digiprem.demo.push_notification_fireBase.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
