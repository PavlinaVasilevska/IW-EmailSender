package com.example.EmailSender.repository;

import com.example.EmailSender.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
    Optional<Role> getRoleByUuid(String uuid);
    Optional<Role>findByUuid(String uuid);

}
