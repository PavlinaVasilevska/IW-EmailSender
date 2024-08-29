package com.example.EmailSender.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RoleDTO extends BaseDTO {

    @JsonProperty("roleName")
    @NotBlank(message = "Name for Role cannot be empty")
    private String name;

}