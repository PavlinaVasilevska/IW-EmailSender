package com.example.EmailSender.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTemplateDTO extends BaseDTO{

    @NotBlank(message = "Subject is mandatory")
    private String subject;

    @NotBlank(message = "Body is mandatory")
    private String body;

}