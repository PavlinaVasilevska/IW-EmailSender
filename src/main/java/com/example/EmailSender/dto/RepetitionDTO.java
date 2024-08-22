package com.example.EmailSender.dto;

import com.example.EmailSender.enumeration.RepetitionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter

public class RepetitionDTO extends BaseDTO{

    @NotNull(message = "Frequency is mandatory")
    private RepetitionEnum frequency;

    @NotNull(message = "Number of tries is mandatory")
    private Integer numberOfTries;


}
