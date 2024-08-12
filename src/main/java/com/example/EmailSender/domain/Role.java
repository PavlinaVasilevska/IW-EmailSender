package com.example.EmailSender.domain;
import com.example.EmailSender.infrastructure.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {

    @Column(name = "role_name", unique = true, nullable = false)
    private String name;


}