package phunla2784.edu.vn.website.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.security.JwtUtil;
import phunla2784.edu.vn.website.security.TokenBlacklistRedis;
import phunla2784.edu.vn.website.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistRedis tokenBlacklist;

    public AuthController(JwtUtil jwtUtil, TokenBlacklistRedis tokenBlacklist) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklist = tokenBlacklist;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRespond<LoginRespond>> login(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        LoginRespond loginRespond = authService.login(
                credentials.get("email"),
                credentials.get("password")
        );
        // Gửi refresh token qua HttpOnly cookie
        var cookie = new Cookie("refreshToken", authService.generaRefreshToken(loginRespond.getUserRespond().getEmail()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        response.addCookie(cookie);
        return ResponseEntity.ok(ApiRespond.success("Login successful", loginRespond));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiRespond<?>> refreshToken(HttpServletRequest request) {
        // Lấy refresh token từ cookie
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("refreshToken".equals(c.getName())) {
                    refreshToken = c.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.badRequest()
                    .body(new ApiRespond("4001", "Refresh token không hợp lệ", null));
        }
        String newAccessToken = jwtUtil.generateAccessToken(jwtUtil.extractEmail(refreshToken),jwtUtil.extractRole(refreshToken));
        String newRefreshToken = jwtUtil.generateRefreshToken(jwtUtil.extractEmail(refreshToken),jwtUtil.extractRole(refreshToken));

        // Cập nhật lại cookie với refresh token mới
        Cookie cookie = new Cookie("refreshToken", newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
        ((HttpServletResponse) request.getAttribute("response")).addCookie(cookie);

        return ResponseEntity.ok(
                new ApiRespond("2000", "Refresh thành công",
                        Map.of("accessToken", newAccessToken))
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiRespond> logout(HttpServletRequest request) {
        String token = parseJwt(request);
        if (token != null && jwtUtil.validateToken(token)) {
            long expirationSeconds = jwtUtil.getRemainingSeconds(token);
            tokenBlacklist.addToken(token, expirationSeconds);
        }
        return ResponseEntity.ok(new ApiRespond("2000", "Đã logout thành công", null));
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}