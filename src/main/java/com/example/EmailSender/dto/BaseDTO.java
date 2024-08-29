package com.example.EmailSender.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDTO {

    private String uuid;
    private LocalDateTime createdOn;
}
