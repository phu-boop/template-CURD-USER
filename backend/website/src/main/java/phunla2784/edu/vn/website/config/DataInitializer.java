package phunla2784.edu.vn.website.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import phunla2784.edu.vn.website.entity.Permission;
import phunla2784.edu.vn.website.entity.Role;
import phunla2784.edu.vn.website.entity.User;
import phunla2784.edu.vn.website.enums.PermissionName;
import phunla2784.edu.vn.website.enums.RoleName;
import phunla2784.edu.vn.website.exception.AppException;
import phunla2784.edu.vn.website.exception.ErrorCode;
import phunla2784.edu.vn.website.repository.PermissionRepository;
import phunla2784.edu.vn.website.repository.RoleRepository;
import phunla2784.edu.vn.website.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            if(roleRepository.findByName(RoleName.ADMIN.getRoleName()).isEmpty()){
                Set<Permission> permissions = new HashSet<>();
                Permission permission = new Permission();
                permission.setName(PermissionName.DELETE.getPermissionName());
                permissions.add(permission);
                permissionRepository.save(permission);
                Role role = new Role();
                role.setName(RoleName.ADMIN.getRoleName());
                role.setPermissions(permissions);
                roleRepository.save(role);
            }
            if(roleRepository.findByName(RoleName.USER.getRoleName()).isEmpty()){
                Set<Permission> permissions = new HashSet<>();
                Permission permission = new Permission();
                permission.setName(PermissionName.READ.getPermissionName());
                permissions.add(permission);
                permissionRepository.save(permission);
                Role role = new Role();
                role.setName(RoleName.USER.getRoleName());
                role.setPermissions(permissions);
                roleRepository.save(role);
            }
            Set<Role> roles = new HashSet<>();
            Role role = roleRepository.findByName(RoleName.ADMIN.getRoleName())
                    .orElseThrow(() -> new AppException(ErrorCode.DATABASE_ERROR));
            roles.add(role);
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123123123"));
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
