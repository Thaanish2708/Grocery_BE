package com.grocery.service;

import com.grocery.payload.LoginDto;
import com.grocery.payload.RegisterDto;

public interface UserService {
    boolean checkForAccount(String email);

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
