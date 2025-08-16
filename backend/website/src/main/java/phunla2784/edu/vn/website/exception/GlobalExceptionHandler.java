package phunla2784.edu.vn.website.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiRespond<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ApiRespond<?> apiRespond = new ApiRespond<>();
        String defaultMsg = Optional.ofNullable(ex.getBindingResult().getFieldError())
            .map(FieldError::getDefaultMessage)
            .orElse("VALIDATION_ERROR");
        ErrorCode errorCode = ErrorCode.valueOf(defaultMsg);
        apiRespond.setCode(errorCode.getCode());
        apiRespond.setMessage(errorCode.getMessage());
        return ResponseEntity.ok().body(apiRespond);
    }
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiRespond<?>> handleAppException(AppException ex){
        ApiRespond<?> apiRespond = new ApiRespond<>();
        apiRespond.setCode(ex.getErrorCode().getCode());
        apiRespond.setMessage(ex.getErrorCode().getMessage());
        return ResponseEntity.ok().body(apiRespond);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiRespond<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        ApiRespond<?> apiRespond = new ApiRespond<>();
        apiRespond.setCode(ErrorCode.BAD_REQUEST.getCode());
        apiRespond.setMessage(ErrorCode.BAD_REQUEST.getMessage());
        return ResponseEntity.ok().body(apiRespond);
    }
}
