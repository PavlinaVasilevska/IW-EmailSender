package com.example.EmailSender.service;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;

import java.util.List;

public interface RoleService{
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO getRoleByUuid(String uuid);
    RoleDTO getRoleByName(String name);
    Role getRoleByname(String name);
    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(String uuid, RoleDTO roleDTO);
    void deleteRole(String uuid);
}
