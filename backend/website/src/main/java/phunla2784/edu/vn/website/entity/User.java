package phunla2784.edu.vn.website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import phunla2784.edu.vn.website.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;
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

    public String getRoleToString() {
        if (this.roles == null || this.roles.isEmpty()) {
            return "";
        }
        return this.roles.stream()
                .map(Role::getName)
                .collect(Collectors.joining());
    }
}
