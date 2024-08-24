package com.example.EmailSender.domain;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "OCCURRENCE")
public class Occurrence extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "error_description")
    private String errorDescription;

    @ManyToOne
    @JoinColumn(name = "email_job_id", nullable = false)
    private EmailJob emailJob;
}
