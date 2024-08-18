package com.example.EmailSender.dto;

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
public class RepetitionDTO extends BaseDTO{

    @NotBlank(message = "Frequency is mandatory")
    private String frequency;

    @NotNull(message = "Number of tries is mandatory")
    private Integer numberOfTries;


}
