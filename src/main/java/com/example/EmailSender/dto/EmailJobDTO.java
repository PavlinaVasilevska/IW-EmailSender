package com.example.EmailSender.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailJobDTO extends BaseDTO{

    private UserDTO sender;

    private EmailTemplateDTO emailTemplate;

    @NotNull(message = "Start date is mandatory")
    private Date startDate;

    @NotNull(message = "End date is mandatory")
    private Date endDate;

    @NotBlank(message = "Repetition is mandatory")
    private RepetitionDTO repetition;

    @NotNull(message = "Enabled status is mandatory")
    private Boolean enabled;

    private String receivers;



}
