package com.example.EmailSender.dto;
import com.example.EmailSender.enumeration.RepetitionEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter

public class EmailJobDTO extends BaseDTO {

    private UserDTO sender;

    private EmailTemplateDTO emailTemplate;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Repetition frequency is mandatory")
    private RepetitionEnum frequency;

    @NotNull(message = "Enabled status is mandatory")
    private Boolean enabled;

    private String receivers;
}
