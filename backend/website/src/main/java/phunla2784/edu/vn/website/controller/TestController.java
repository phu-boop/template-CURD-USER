package phunla2784.edu.vn.website.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/admin")
    public ResponseEntity<ApiRespond<?>> admin() {
        ApiRespond<?> apiRespond = new ApiRespond<>();
        apiRespond.success("hello admin", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiRespond);
    }
    @GetMapping("/user")
        public ResponseEntity<ApiRespond<?>> user() {
        ApiRespond<?> apiRespond = new ApiRespond<>();
        apiRespond.success("hello user",null);
        return ResponseEntity.status(HttpStatus.OK).body(apiRespond);
    }
}
