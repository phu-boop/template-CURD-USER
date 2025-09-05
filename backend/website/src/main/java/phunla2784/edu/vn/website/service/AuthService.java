// src/main/java/phunla2784/edu/vn/website/service/AuthService.java
package phunla2784.edu.vn.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.entity.User;
import phunla2784.edu.vn.website.exception.AppException;
import phunla2784.edu.vn.website.exception.ErrorCode;
import phunla2784.edu.vn.website.mapper.UserMapper;
import phunla2784.edu.vn.website.repository.UserRepository;
import phunla2784.edu.vn.website.security.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserMapper userMapper;

    public LoginRespond login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateAccessToken(user.getEmail(), user.getRoleToString());
            UserRespond userRespond = userMapper.usertoUserRespond(user);
            LoginRespond loginRespond = new LoginRespond(userRespond, token);
            return loginRespond;
        } else {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }
    public String generaRefreshToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return jwtUtil.generateRefreshToken(user.getEmail(), user.getRoleToString());
    }
}
