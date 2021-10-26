package ede.desafiogat.domain.mappers;

import ede.desafiogat.domain.models.Card;
import ede.desafiogat.trello.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    Card toModel(CardDTO cardDTO);
    CardDTO toDTO(Card card);
}
