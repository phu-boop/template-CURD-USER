package phunla2784.edu.vn.website.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //
    SUCCESS("1000", "Success", HttpStatus.OK),

    // ===== 2xxx - Client errors =====
    VALIDATION_ERROR("2001", "Validation failed", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("2002", "Bad request", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("2003", "HTTP method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE("2004", "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    MISSING_REQUIRED_FIELD("2005", "Missing required field", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT("2006", "Email format is invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_TOO_SHORT("2007", "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_FORMAT("2008", "Password must include uppercase, lowercase, number, and special character", HttpStatus.BAD_REQUEST),
    AGE_TOO_YOUNG("2009", "User must be at least 18 years old", HttpStatus.BAD_REQUEST),
    PHONE_INVALID_FORMAT("2010", "Phone number must be 10-12 digits", HttpStatus.BAD_REQUEST),

    // ===== 3xxx - Server errors =====
    INTERNAL_ERROR("3001", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE("3002", "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    TIMEOUT("3003", "Request timeout", HttpStatus.REQUEST_TIMEOUT),

    // ===== 4xxx - Auth & Authorization =====
    UNAUTHORIZED("4001", "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("4002", "Access denied", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("4003", "Token expired", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("4004", "Invalid token", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("4005", "Account is locked", HttpStatus.FORBIDDEN),
    TOKEN_LOGGED_OUT("4006", "Token logged out", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("4007","Invalid refresh token",HttpStatus.UNAUTHORIZED),
    // ===== 5xxx - Data & DB errors =====
    USER_NOT_FOUND("5001", "User not found", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("5002", "Email already exists", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS("5003", "Phone number already exists", HttpStatus.CONFLICT),
    DATA_NOT_FOUND("5004", "Data not found", HttpStatus.NOT_FOUND),
    DATA_ALREADY_EXISTS("5005", "Data already exists", HttpStatus.CONFLICT),
    DATABASE_ERROR("5006", "Database error", HttpStatus.INTERNAL_SERVER_ERROR),
    CONSTRAINT_VIOLATION("5007", "Database constraint violation", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("5008", "Invalid password", HttpStatus.BAD_REQUEST),

    // ===== 6xxx - Business logic errors =====
    PAYMENT_FAILED("6001", "Payment failed", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE("6002", "Insufficient balance", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND("6003", "Order not found", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK("6004", "Product out of stock", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
