package phunla2784.edu.vn.website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import phunla2784.edu.vn.website.converter.RoleSetConverter;
import phunla2784.edu.vn.website.enums.Gender;
import phunla2784.edu.vn.website.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String email;
    String password;
    @Convert(converter = RoleSetConverter.class)
    private Set<Role> roles;
    String name;
    String fullName;
    String phone;
    String address;
    LocalDate birthday;
    String city;
    String country;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
