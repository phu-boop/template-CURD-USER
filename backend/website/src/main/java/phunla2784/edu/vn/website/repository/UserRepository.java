package phunla2784.edu.vn.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phunla2784.edu.vn.website.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    User findByEmail(String email);
}
