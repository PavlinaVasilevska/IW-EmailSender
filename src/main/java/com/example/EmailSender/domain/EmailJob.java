package com.example.EmailSender.domain;
import com.example.EmailSender.enumeration.FrequencyEnum;
import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "EMAIL_JOB")
public class EmailJob extends BaseEntity {

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime  endDate;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private FrequencyEnum frequency;
}