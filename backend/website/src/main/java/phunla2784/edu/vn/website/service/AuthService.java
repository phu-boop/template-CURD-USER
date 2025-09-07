// src/main/java/phunla2784/edu/vn/website/service/AuthService.java
package phunla2784.edu.vn.website.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.dto.respond.TokenPair;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.entity.User;
import phunla2784.edu.vn.website.exception.AppException;
import phunla2784.edu.vn.website.exception.ErrorCode;
import phunla2784.edu.vn.website.mapper.UserMapper;
import phunla2784.edu.vn.website.repository.UserRepository;
import phunla2784.edu.vn.website.security.JwtUtil;
import phunla2784.edu.vn.website.security.TokenBlacklistRedis;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final TokenBlacklistRedis tokenBlacklist;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       UserMapper userMapper,
                       TokenBlacklistRedis tokenBlacklist) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.tokenBlacklist = tokenBlacklist;
    }


    public LoginRespond login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateAccessToken(user.getEmail(), user.getRoleToString());
            UserRespond userRespond = userMapper.usertoUserRespond(user);
            return new LoginRespond(userRespond, token);
        } else {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

      public LoginRespond getCurrentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        UserRespond userRespond = userMapper.usertoUserRespond(user);

        userRespond.setBirthday(null);

        return new LoginRespond(userRespond, null);
    }

    public String generateRefreshToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return jwtUtil.generateRefreshToken(user.getEmail(), user.getRoleToString());
    }

    public TokenPair newRefreshTokenAndAccessToken(HttpServletRequest request) {
        // Lấy refresh token từ cookie
        String refreshToken = getRefreshTokenFromRequest(request);
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        if (tokenBlacklist.contains(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_LOGGED_OUT);
        }
        String newAccessToken = jwtUtil.generateAccessToken(
                jwtUtil.extractEmail(refreshToken),
                jwtUtil.extractRole(refreshToken)
        );
        String newRefreshToken = jwtUtil.generateRefreshToken(
                jwtUtil.extractEmail(refreshToken),
                jwtUtil.extractRole(refreshToken)
        );
        return new TokenPair(newAccessToken, newRefreshToken);
    }

    public void addTokenBlacklist(HttpServletRequest request) {
        String token = parseJwt(request);
        String tokenRefresh = getRefreshTokenFromRequest(request);
        if (token == null || tokenRefresh == null) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        if (!jwtUtil.validateToken(token) || !jwtUtil.validateToken(tokenRefresh)) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        long expirationSeconds_refresh = jwtUtil.getRemainingSeconds(tokenRefresh);
        long expirationSeconds_access = jwtUtil.getRemainingSeconds(token);
        tokenBlacklist.addToken(tokenRefresh, expirationSeconds_refresh);
        tokenBlacklist.addToken(token, expirationSeconds_access);

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("refreshToken".equals(c.getName())) {
                    refreshToken = c.getValue();
                    break;
                }
            }
        }
        return refreshToken;
    }

}
