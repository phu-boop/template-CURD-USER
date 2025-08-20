package phunla2784.edu.vn.website.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespond {
    UserRespond userRespond;
    String token;
}
