package com.example.EmailSender.domain;
import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity

@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate extends BaseEntity {

    @NotBlank(message = "Subject cannot be blank.")
    @Size(max = 255, message = "Subject cannot be longer than 255 characters.")
    @Column(name = "subject", nullable = false)
    private String subject;

    @NotBlank(message = "Body cannot be blank.")
    @Column(name = "body", nullable = false)
    private String body;

    @OneToMany(mappedBy = "emailTemplate")
    private List<EmailJob> emailJobs;
}
