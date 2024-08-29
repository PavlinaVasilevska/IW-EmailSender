package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.RoleMapper;
import com.example.EmailSender.repository.RoleRepository;
import com.example.EmailSender.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDTO getRoleByUuid(String uuid) {
        Role role = roleRepository.findByUuid(uuid);
        if(role == null) {
            throw new ResourceNotFoundException("Role with UUID: " + uuid + " not found");
        }
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDTO getRoleByName(String name) {
        Role role = roleRepository.findByName(name);
        if(role== null)
        {
            throw new ResourceNotFoundException("Role with UUID: " + name + " not found");
        }
        return roleMapper.toDto(role);
    }

    @Override
    public Role getRoleByname(String name) {
        Role role = roleRepository.findByName(name);
        if(role== null)
        {
            throw new ResourceNotFoundException("Role with UUID: " + name + " not found");
        }

        return role;
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO updateRole(String uuid, RoleDTO roleDTO) {
        Role existingRole = roleRepository.findByUuid(uuid);
        if (existingRole == null) {
            throw new ResourceNotFoundException("Role with UUID: " + uuid + " not found");
        }

        if (roleDTO.getUuid() != null && !roleDTO.getUuid().equals(existingRole.getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'uuid' field cannot be updated.");
        }
        if (roleDTO.getCreatedOn() != null && !roleDTO.getCreatedOn().equals(existingRole.getCreatedOn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'createdOn' field cannot be updated.");
        }

        roleMapper.updateRoleFromDto(roleDTO, existingRole);

        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.toDto(updatedRole);
    }


    @Override
    public void deleteRole(String uuid) {
        Role role = roleRepository.findByUuid(uuid);
        if(role == null)
        {
            throw new ResourceNotFoundException("Role with UUID: " + uuid + " not found");
        }
        roleRepository.delete(role);
    }
}