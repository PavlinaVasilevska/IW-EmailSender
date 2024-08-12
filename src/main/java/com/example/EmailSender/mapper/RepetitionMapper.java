package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.Repetition;
import com.example.EmailSender.dto.RepetitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface RepetitionMapper {
    RepetitionMapper INSTANCE = Mappers.getMapper(RepetitionMapper.class);

    RepetitionDTO repetitionToRepetitionDTO(Repetition repetition);
    Repetition repetitionDTOToRepetition(RepetitionDTO repetitionDTO);

}