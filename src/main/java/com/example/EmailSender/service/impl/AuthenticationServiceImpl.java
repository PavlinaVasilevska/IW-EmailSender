package com.example.EmailSender.service.impl;

import com.example.EmailSender.dto.AuthRequest;
import com.example.EmailSender.dto.AuthResponse;
import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.service.AuthenticationService;
import com.example.EmailSender.config.JwtService;
import com.example.EmailSender.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private  final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {

        UserDetails userDTO = userService.loadUserByUsername(authRequest.getUsername());
        if(!passwordEncoder.matches(authRequest.getPassword(), userDTO.getPassword())){
            throw new BadCredentialsException("Username and password don't match");
        }
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwtService.generateToken(authRequest.getUsername()));
        return authResponse;
    }

    @Override
    public UserDTO register(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);

    }
}
