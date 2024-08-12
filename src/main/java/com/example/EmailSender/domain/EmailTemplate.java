package com.example.EmailSender.domain;

import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate extends BaseEntity {

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "body", nullable = false)
    private String body;

    @OneToMany(mappedBy = "emailTemplate")
    private List<EmailJob> emailJobs;
}
