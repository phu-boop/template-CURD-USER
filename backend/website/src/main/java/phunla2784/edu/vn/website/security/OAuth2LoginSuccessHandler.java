package phunla2784.edu.vn.website.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.entity.Role;
import phunla2784.edu.vn.website.entity.User;
import phunla2784.edu.vn.website.enums.RoleName;
import phunla2784.edu.vn.website.exception.AppException;
import phunla2784.edu.vn.website.exception.ErrorCode;
import phunla2784.edu.vn.website.mapper.UserMapper;
import phunla2784.edu.vn.website.repository.RoleRepository;
import phunla2784.edu.vn.website.repository.UserRepository;
import phunla2784.edu.vn.website.service.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy thông tin user từ Google
        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String givenName = oauthUser.getAttribute("given_name");
        //String picture = oauthUser.getAttribute("picture");

        Set<Role> roles = new HashSet<>();
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            roles.add(roleRepository.findByName(RoleName.USER.getRoleName())
                    .orElseThrow(() -> new AppException(ErrorCode.DATABASE_ERROR)));
            User newUser = User.builder()
                    .email(email)
                    .name(givenName)
                    .fullName(name)
                    .roles(roles)
                    .build();
            return userRepository.save(newUser);
        });

        String accessToken = jwtUtil.generateAccessToken(email, roles.toString());
        String refreshToken = jwtUtil.generateRefreshToken(email, roles.toString());


    // ✅ Set refresh token trong cookie HttpOnly
    Cookie cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);   // chặn JS đọc
    cookie.setSecure(false);    // nếu chạy local dev thì để false, deploy HTTPS thì true
    cookie.setPath("/auth/refresh"); // chỉ gửi cookie khi gọi API refresh
    cookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
    response.addCookie(cookie);

        UserRespond userRespond = userMapper.usertoUserRespond(user);
    LoginRespond loginRespond = new LoginRespond(userRespond,accessToken);

    ApiRespond<Object> apiResponse = ApiRespond.success("Login with Google success", loginRespond);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    response.getWriter().flush();

    }
}
