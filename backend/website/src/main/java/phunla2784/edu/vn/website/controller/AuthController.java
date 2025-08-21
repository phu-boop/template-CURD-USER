// src/main/java/phunla2784/edu/vn/website/controller/AuthController.java
package phunla2784.edu.vn.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.LoginRespond;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiRespond<LoginRespond>> login(@RequestBody Map<String, String> credentials) {
        LoginRespond loginRespond = authService.login(
                credentials.get("email"),
                credentials.get("password")
        );
        return ResponseEntity.ok(ApiRespond.success("Login successful", loginRespond));
    }
}