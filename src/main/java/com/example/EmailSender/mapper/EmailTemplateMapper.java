package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.dto.EmailTemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailTemplateMapper {

    EmailTemplateMapper INSTANCE = Mappers.getMapper(EmailTemplateMapper.class);


    EmailTemplateDTO emailTemplateToEmailTemplateDTO(EmailTemplate emailTemplate);

    EmailTemplate emailTemplateDTOToEmailTemplate(EmailTemplateDTO emailTemplateDTO);

    List<EmailTemplateDTO> emailTemplatesToEmailTemplateDTOs(List<EmailTemplate> emailTemplates);

    List<EmailTemplate> emailTemplateDTOsToEmailTemplates(List<EmailTemplateDTO> emailTemplateDTOs);
}
