package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.dto.EmailJobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {UserMapper.class, EmailTemplateMapper.class})
public interface EmailJobMapper {

    EmailJobDTO toDto(EmailJob emailJob);
    EmailJob toEntity(EmailJobDTO emailJobDTO);
    void updateFromDto(EmailJobDTO emailJobDTO, @MappingTarget EmailJob emailJob);
    }