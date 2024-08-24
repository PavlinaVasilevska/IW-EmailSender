package com.example.EmailSender.repository;
import com.example.EmailSender.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
    Role findByUuid(String uuid);

}
