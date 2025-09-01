package phunla2784.edu.vn.website.dto.respond;


import org.jetbrains.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import phunla2784.edu.vn.website.exception.ErrorCode;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRespond<T> {
    String code;
    String message;
    @Nullable
    T data;
    public static <T> ApiRespond<T> success(String message, T data) {
        return new ApiRespond<>(ErrorCode.SUCCESS.getCode(), message, data);
    }
}
