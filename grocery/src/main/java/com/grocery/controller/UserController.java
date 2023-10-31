package com.grocery.controller;

import com.grocery.payload.LoginDto;
import com.grocery.payload.RegisterDto;
import com.grocery.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Operations pertaining to user entities")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("check")
    @Operation(summary = "Check email in DB", description = "Endpoint to check email is registered or not")
    private boolean checkForAccount(@RequestParam("mail") String email){
        return userService.checkForAccount(email);
    }

    @PostMapping("/login")
    @Operation(summary = "Login to application", description = "Endpoint to login to the app")
    private String login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user to application", description = "Endpoint to register new user")
    private String register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }
}
