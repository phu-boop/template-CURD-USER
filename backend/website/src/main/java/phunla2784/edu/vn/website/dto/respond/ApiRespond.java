package phunla2784.edu.vn.website.dto.respond;


import org.jetbrains.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRespond<T> {
    String code;
    String message;
    @Nullable
    T data;
}
