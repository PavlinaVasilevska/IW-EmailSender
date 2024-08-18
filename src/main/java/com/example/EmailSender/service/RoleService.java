package com.example.EmailSender.service;
import com.example.EmailSender.dto.RoleDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



public interface RoleService{
    RoleDTO createRole(RoleDTO roleDTO);
    Optional<RoleDTO> getRoleByUuid(String uuid);
    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(String uuid, RoleDTO roleDTO);
    void deleteRole(String uuid);
}
