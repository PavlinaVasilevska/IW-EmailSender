package com.example.EmailSender.dto;
import com.example.EmailSender.enumeration.FrequencyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter

public class EmailJobDTO extends BaseDTO {

    @NotNull(message = "Sender is mandatory")
    private UserDTO sender;

    @NotNull(message = "Email template is mandatory")
    private EmailTemplateDTO emailTemplate;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Repetition frequency is mandatory")
    private FrequencyEnum frequency;

    @NotNull(message = "Enabled status is mandatory")
    private Boolean enabled;

    @NotBlank(message = "Receivers are mandatory")
    private String receivers;
}
