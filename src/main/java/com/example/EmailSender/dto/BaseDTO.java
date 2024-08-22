package com.example.EmailSender.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BaseDTO {
    private String uuid;
    private Date createdOn;
}
