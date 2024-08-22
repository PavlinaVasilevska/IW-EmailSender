package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.Repetition;
import com.example.EmailSender.dto.RepetitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RepetitionMapper {

    RepetitionDTO toDto(Repetition repetition);

    Repetition toEntity(RepetitionDTO repetitionDTO);
}