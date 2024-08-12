package com.example.EmailSender.domain;

import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OCCURRENCE")
public class Occurrence extends BaseEntity {

    @Column(name = "status")
    private Integer status;

    @Column(name = "error_description")
    private String errorDescription;

    @ManyToOne
    @JoinColumn(name = "email_job_id", nullable = false)
    private EmailJob emailJob;
}