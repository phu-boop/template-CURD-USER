package phunla2784.edu.vn.website.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import phunla2784.edu.vn.website.entity.Role;
import phunla2784.edu.vn.website.enums.Gender;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRespond {
    Long id;
    String email;
    String name;
    String fullName;
    String phone;
    String address;
    String city;
    String country;
    LocalDate birthday;
    Gender gender;
    Set<Role> roles;
}
