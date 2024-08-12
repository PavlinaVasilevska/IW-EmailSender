package com.example.EmailSender.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseDTO {

    @JsonProperty("roleName")
    @NotNull(message = "Name for Role cannot be null")
    @NotBlank(message = "Name for Role cannot be empty")
    private String name;



}