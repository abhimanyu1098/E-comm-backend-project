package com.abhimanyu.ecomapplication.Controller;
import com.abhimanyu.ecomapplication.dto.UserRequest;
import com.abhimanyu.ecomapplication.dto.UserResponse;
import com.abhimanyu.ecomapplication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchUsers(),HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable Long id) {
            return userService.fetchId(id)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/users")
    public String createUsers(@RequestBody UserRequest userRequest) {
        userService.addUsers(userRequest);
        return "user added sucessfully";
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable long id, @RequestBody UserRequest updateUserRequest) {

                boolean updated= userService.update(id, updateUserRequest);
                if (updated) {
                    return ResponseEntity.ok("user gets updated");
                }
                return ResponseEntity.notFound().build();
    }
}
