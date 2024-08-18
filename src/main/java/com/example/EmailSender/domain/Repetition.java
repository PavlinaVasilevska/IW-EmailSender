package com.example.EmailSender.domain;

import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "REPETITION")
public class Repetition extends BaseEntity {

    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "number_of_tries", nullable = false)
    private Integer numberOfTries;

    @OneToMany(mappedBy = "repetition")
    private List<EmailJob> emailJobs;

}