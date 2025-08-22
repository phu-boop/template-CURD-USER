package phunla2784.edu.vn.website.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import phunla2784.edu.vn.website.enums.Gender;

import java.time.LocalDate;

@Data
public class UserRequest {
    @NotBlank(message = "MISSING_REQUIRED_FIELD")
    @Email(message = "INVALID_EMAIL_FORMAT")
    String email;
    @NotBlank(message = "MISSING_REQUIRED_FIELD")
    @Size(min=8, message = "PASSWORD_TOO_SHORT")
    String password;
    @NotBlank(message = "MISSING_REQUIRED_FIELD")
    String name;
    String fullName;
    String phone;
    String address;
    LocalDate birthday;
    String city;
    String country;
    Gender gender;
}
