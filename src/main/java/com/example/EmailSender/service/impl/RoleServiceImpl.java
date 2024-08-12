package com.example.EmailSender.service.impl;

import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.repository.RoleRepository;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.mapper.RoleMapper;
import com.example.EmailSender.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        // Validate the input DTO
        if (roleDTO == null || roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("RoleDTO or its name cannot be null or empty");
        }

        // Check if a role with the same name already exists
        Optional<Role> existingRole = roleRepository.findByName(roleDTO.getName());
        if (existingRole.isPresent()) {
            // Return an appropriate response or throw an exception
            throw new ResourceNotFoundException("Role with name " + roleDTO.getName() + " already exists");
        }

        // Convert DTO to entity
        Role role = roleMapper.roleDTOToRole(roleDTO);
        role.setUuid(UUID.randomUUID().toString());

        // Save the entity
        Role savedRole = roleRepository.save(role);

        // Convert entity back to DTO
        return roleMapper.roleToRoleDTO(savedRole);
    }

    @Override
    public Optional<RoleDTO> getRoleByUuid(String uuid) {
        return roleRepository.getRoleByUuid(uuid)
                .map(roleMapper::roleToRoleDTO);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.rolesToRoleDTOs(roles);
    }

    @Override
    public RoleDTO updateRole(String uuid, RoleDTO roleDTO) {
        Role existingRole = roleRepository.getRoleByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Role with UUID " + uuid + " not found"));

        // Ensure the name field is not null or empty
        if (roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }

        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return roleMapper.roleToRoleDTO(updatedRole);
    }

    @Override
    public void deleteRole(String uuid) {
        Role existingRole = roleRepository.getRoleByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Role with UUID " + uuid + " not found"));

        roleRepository.delete(existingRole);
    }
}