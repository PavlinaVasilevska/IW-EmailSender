package com.example.EmailSender.domain;

import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(value = 0, message = "Status must be either 0 or 1")
    @Max(value = 1, message = "Status must be either 0 or 1")
    private Integer status;

    @Column(name = "error_description")
    @NotBlank(message = "Error description cannot be blank")
    private String errorDescription;

    @ManyToOne
    @JoinColumn(name = "email_job_id", nullable = false)
    private EmailJob emailJob;
}