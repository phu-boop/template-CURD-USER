// src/main/java/phunla2784/edu/vn/website/service/AuthService.java
package phunla2784.edu.vn.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import phunla2784.edu.vn.website.entity.User;
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

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        System.out.println("Role"+user.getRole());
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
