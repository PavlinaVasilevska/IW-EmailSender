package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO roleDTO);

    List<RoleDTO> toDtoList(List<Role> roles);

    List<Role> toEntityList(List<RoleDTO> roleDTOs);
}