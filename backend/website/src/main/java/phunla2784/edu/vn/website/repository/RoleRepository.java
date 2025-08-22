package phunla2784.edu.vn.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phunla2784.edu.vn.website.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    public Optional<Role> findByName(String name);
}
