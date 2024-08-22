package com.example.EmailSender.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class EmailJobDTO extends BaseDTO{

    private UserDTO sender;

    private EmailTemplateDTO emailTemplate;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    @NotBlank(message = "Repetition is mandatory")
    private RepetitionDTO repetition;

    @NotNull(message = "Enabled status is mandatory")
    private Boolean enabled;

    private String receivers;



}
