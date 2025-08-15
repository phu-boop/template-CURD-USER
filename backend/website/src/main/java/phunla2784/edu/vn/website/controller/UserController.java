package phunla2784.edu.vn.website.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import phunla2784.edu.vn.website.dto.request.UserRequest;
import phunla2784.edu.vn.website.dto.respond.ApiRespond;
import phunla2784.edu.vn.website.dto.respond.UserRespond;
import phunla2784.edu.vn.website.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiRespond<UserRespond> createUser(@RequestBody @Valid UserRequest userRequest){
        ApiRespond<UserRespond> apiRespond = new ApiRespond<>();
        apiRespond.setCode("1000");
        apiRespond.setMessage("Create User Successfully");
        apiRespond.setData(userService.createUser(userRequest));
        return apiRespond;
    }
    @PutMapping("/{id}")
    public ApiRespond<UserRespond> updateUser(@PathVariable  Long id,@RequestBody @Valid UserRequest userRequest){
        ApiRespond<UserRespond> apiRespond = new ApiRespond<>();
        apiRespond.setCode("1000");
        apiRespond.setMessage("Update User Successfully");
        apiRespond.setData(userService.updateUser(id, userRequest));
        return apiRespond;
    }
    @DeleteMapping("/{id}")
    public ApiRespond<?> deleteUser(@PathVariable Long id){
        ApiRespond<UserRespond> apiRespond = new ApiRespond<>();
        apiRespond.setCode("1000");
        apiRespond.setMessage("Delete User Successfully");
        userService.deleteUser(id);
        return apiRespond;
    }
}
