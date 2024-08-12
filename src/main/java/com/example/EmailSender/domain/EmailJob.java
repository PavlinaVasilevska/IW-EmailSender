package com.example.EmailSender.domain;

import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "EMAIL_JOB")
public class EmailJob extends BaseEntity {

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "email_template_id")
    private EmailTemplate emailTemplate;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "receivers")
    private String receivers;

    @ManyToOne
    @JoinColumn(name = "repetition_id")
    private Repetition repetition;


}