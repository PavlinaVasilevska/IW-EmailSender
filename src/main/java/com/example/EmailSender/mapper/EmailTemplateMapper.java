package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.dto.EmailTemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailTemplateMapper {

    EmailTemplateDTO toDto(EmailTemplate emailTemplate);

    EmailTemplate toEntity(EmailTemplateDTO emailTemplateDTO);

    List<EmailTemplateDTO> toDtoList(List<EmailTemplate> emailTemplates);

    List<EmailTemplate> toEntityList(List<EmailTemplateDTO> emailTemplateDTOs);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    void updateEmailTemplateFromDto(EmailTemplateDTO emailTemplateDTO, @MappingTarget EmailTemplate emailTemplate);
}
