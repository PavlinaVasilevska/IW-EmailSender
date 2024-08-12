package com.example.EmailSender.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on", updatable = false)
    private Date createdOn;

    @Column(name = "uuid", updatable = false)
    private String uuid;

    @PrePersist
    public void init() {
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
        createdOn = new Date();
    }

}