package phunla2784.edu.vn.website.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.dto.respond.TokenPair;
import phunla2784.edu.vn.website.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRespond<LoginRespond>> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        LoginRespond loginRespond = authService.login(
                credentials.get("email"),
                credentials.get("password")
        );
        // Gửi refresh token qua HttpOnly cookie
        var cookie = new Cookie("refreshToken", authService.generateRefreshToken(loginRespond.getUserRespond().getEmail()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiRespond.success("Login successful", loginRespond));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiRespond<LoginRespond>> getCurrentUser() {
        LoginRespond loginRespond = authService.getCurrentUser();
        return ResponseEntity.ok(ApiRespond.success("Get current user successful", loginRespond));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiRespond<?>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        TokenPair tokenPair = authService.newRefreshTokenAndAccessToken(request);
        // Cập nhật lại cookie với refresh token mới
        Cookie cookie = new Cookie("refreshToken", tokenPair.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        response.addCookie(cookie);
        return ResponseEntity.ok(
                ApiRespond.success("RefreshToken successful", tokenPair));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiRespond<?>> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.addTokenBlacklist(request);
        // Xoá refresh token cookie
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiRespond.success("Logout successful", null));
    }
}