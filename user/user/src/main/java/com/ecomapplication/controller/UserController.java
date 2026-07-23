package com.ecomapplication.controller;


import com.ecomapplication.dto.UserRequest;
import com.ecomapplication.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecomapplication.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    //private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUsers(@PathVariable String id) {

        log.info("Request recieved for user: {}", id);
        log.trace("This is trace level- very detailed logs");
        log.info("This is trace level- very detailed logs");
        log.warn("This is trace level- very detailed logs");
        log.error("This is trace level- very detailed logs");
        return userService.fetchId(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String createUsers(@RequestBody UserRequest userRequest) {
        userService.addUsers(userRequest);
        return "user added sucessfully";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsers(@PathVariable String id, @RequestBody UserRequest updateUserRequest) {

                boolean updated= userService.update(id, updateUserRequest);
                if (updated) {
                    return ResponseEntity.ok("user gets updated");
                }
                return ResponseEntity.notFound().build();
    }
}
