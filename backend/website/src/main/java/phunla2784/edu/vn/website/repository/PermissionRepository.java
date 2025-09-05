package phunla2784.edu.vn.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phunla2784.edu.vn.website.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
