package com.example.EmailSender.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @JsonProperty("username")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}