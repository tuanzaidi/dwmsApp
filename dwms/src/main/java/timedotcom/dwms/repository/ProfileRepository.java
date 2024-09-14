package timedotcom.dwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import timedotcom.dwms.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
}
