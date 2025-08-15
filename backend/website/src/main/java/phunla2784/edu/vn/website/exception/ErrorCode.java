package phunla2784.edu.vn.website.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // ===== 2xxx - Client errors =====
    VALIDATION_ERROR("2001", "Validation failed"),
    BAD_REQUEST("2002", "Bad request"),
    METHOD_NOT_ALLOWED("2003", "HTTP method not allowed"),
    UNSUPPORTED_MEDIA_TYPE("2004", "Unsupported media type"),
    MISSING_REQUIRED_FIELD("2005", "Missing required field"),
    INVALID_EMAIL_FORMAT("2006", "Email format is invalid"),
    PASSWORD_TOO_SHORT("2007", "Password must be at least 8 characters"),

    // ===== 3xxx - Server errors =====
    INTERNAL_ERROR("3001", "Internal server error"),
    SERVICE_UNAVAILABLE("3002", "Service unavailable"),
    TIMEOUT("3003", "Request timeout"),

    // ===== 4xxx - Auth & Authorization =====
    UNAUTHORIZED("4001", "Unauthorized"),
    FORBIDDEN("4002", "Access denied"),
    TOKEN_EXPIRED("4003", "Token expired"),
    TOKEN_INVALID("4004", "Invalid token"),
    ACCOUNT_LOCKED("4005", "Account is locked"),

    // ===== 5xxx - Data & DB errors =====
    USER_NOT_FOUND("5001", "User not found"),
    EMAIL_ALREADY_EXISTS("5002", "Email already exists"),
    PHONE_ALREADY_EXISTS("5003", "Phone number already exists"),
    DATA_NOT_FOUND("5004", "Data not found"),
    DATA_ALREADY_EXISTS("5005", "Data already exists"),
    DATABASE_ERROR("5006", "Database error"),
    CONSTRAINT_VIOLATION("5007", "Database constraint violation"),

    // ===== 6xxx - Business logic errors =====
    PAYMENT_FAILED("6001", "Payment failed"),
    INSUFFICIENT_BALANCE("6002", "Insufficient balance"),
    ORDER_NOT_FOUND("6003", "Order not found"),
    PRODUCT_OUT_OF_STOCK("6004", "Product out of stock");

    private final String code;
    private final String message;
}
