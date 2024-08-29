package com.example.EmailSender.api;
import com.example.EmailSender.dto.AuthRequest;
import com.example.EmailSender.dto.AuthResponse;
import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationControleller {

    private final AuthenticationService authenticationService;

    public AuthenticationControleller(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
       return authenticationService.authenticate(authRequest);

    }
    @PostMapping("/register")
    public UserDTO authenticateAndGetToken(@Valid @RequestBody UserDTO user) {
       return authenticationService.register(user);

    }
}
