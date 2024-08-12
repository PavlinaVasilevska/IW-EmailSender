package com.example.EmailSender.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepetitionDTO extends BaseDTO{

    @NotBlank(message = "Frequency is mandatory")
    private String frequency;

    @NotNull(message = "Number of tries is mandatory")
    private Integer numberOfTries;


}
