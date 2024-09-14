package timedotcom.dwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import timedotcom.dwms.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // custom query methods (if any)
    User findByUsername(String username);
}