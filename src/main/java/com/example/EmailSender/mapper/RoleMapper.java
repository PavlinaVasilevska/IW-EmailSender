package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO roleToRoleDTO(Role role);

    Role roleDTOToRole(RoleDTO roleDTO);

    List<RoleDTO> rolesToRoleDTOs(List<Role> roles);

    List<Role> roleDTOsToRoles(List<RoleDTO> roleDTOs);
}