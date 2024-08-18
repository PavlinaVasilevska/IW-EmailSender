package com.example.EmailSender.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OccurrenceDTO extends BaseDTO{
    private Integer status;

    private String errorDescription;

    @NotNull(message = "EmailJob is mandatory")
    private EmailJobDTO emailJob;

}
