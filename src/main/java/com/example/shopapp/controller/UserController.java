package com.example.shopapp.controller;

import com.example.shopapp.dto.UserDto;
import com.example.shopapp.dto.UserLoginDto;
import com.example.shopapp.response.ResponseObject;
import com.example.shopapp.services.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(userService.createUser(userDto))
                .message("Account registration successful")
                .build());

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userDto) {
        try {
            String token = userService.login(userDto.getPhoneNumber(), userDto.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.updateUser(userDto))
                .message("Account registration successful")
                .build());

    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseObject> updateUser(@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.deleteUser(id))
                .message("Delete Account successful")
                .build());

    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseObject> filter(@RequestParam("id") Long id) throws Exception {
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userService.filterUser(id))
                .message("Delete Account successful")
                .build());

    }
}
