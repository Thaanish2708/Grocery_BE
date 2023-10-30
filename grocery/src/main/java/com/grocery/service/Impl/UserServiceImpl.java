package com.grocery.service.Impl;

import com.grocery.entity.User;
import com.grocery.payload.LoginDto;
import com.grocery.payload.RegisterDto;
import com.grocery.repository.UserRepository;
import com.grocery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean checkForAccount(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String login(LoginDto loginDto) {
        if(!checkForAccount(loginDto.getEmail())){
            return "User not found";
        }
        User user = userRepository.findByEmail(loginDto.getEmail());
        return Objects.equals(loginDto.getPassword(), user.getPassword()) ? String.valueOf(user.getId()) :"Incorrect Password";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(checkForAccount(registerDto.getEmail())){
            return "User already exists";
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setPassword(registerDto.getPassword());
        userRepository.save(user);
        return String.valueOf(user.getId());
    }
}
