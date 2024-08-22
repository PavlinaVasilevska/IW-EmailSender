package com.example.EmailSender.dto;

import com.example.EmailSender.enumeration.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OccurrenceDTO extends BaseDTO{
    private StatusEnum status;

    private String errorDescription;

    @NotNull(message = "EmailJob is mandatory")
    private EmailJobDTO emailJob;

}
