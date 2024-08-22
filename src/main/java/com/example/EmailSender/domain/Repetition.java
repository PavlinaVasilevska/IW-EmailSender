package com.example.EmailSender.domain;

import com.example.EmailSender.enumeration.RepetitionEnum;
import com.example.EmailSender.infrastructure.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "REPETITION")
public class Repetition extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private RepetitionEnum frequency;

    @Column(name = "number_of_tries", nullable = false)
    private Integer numberOfTries;

    @OneToMany(mappedBy = "repetition")
    private List<EmailJob> emailJobs;

}