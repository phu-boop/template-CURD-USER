package phunla2784.edu.vn.website.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phunla2784.edu.vn.website.dto.request.UserRequest;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<ApiRespond<UserRespond>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespond.success("Create User Successfully", userService.createUser(userRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiRespond<UserRespond>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(
                ApiRespond.success("Update User Successfully", userService.updateUser(id, userRequest))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespond<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiRespond.success("Delete User Successfully", null));
    }
}
