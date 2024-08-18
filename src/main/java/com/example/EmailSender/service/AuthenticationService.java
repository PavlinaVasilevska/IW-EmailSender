package com.example.EmailSender.service;

import com.example.EmailSender.dto.AuthRequest;
import com.example.EmailSender.dto.AuthResponse;
import com.example.EmailSender.dto.UserDTO;

public interface AuthenticationService {

    public AuthResponse authenticate(AuthRequest authRequest);

    UserDTO register(UserDTO user);
}
