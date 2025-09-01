package phunla2784.edu.vn.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phunla2784.edu.vn.website.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAll();
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    Optional<User> getUserById(Long id);
    Optional<User> findByEmail(String email);
}
