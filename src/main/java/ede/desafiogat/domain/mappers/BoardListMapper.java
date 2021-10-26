package ede.desafiogat.domain.mappers;

import ede.desafiogat.domain.models.BoardList;
import ede.desafiogat.trello.dto.BoardListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardListMapper {

    BoardListMapper INSTANCE = Mappers.getMapper(BoardListMapper.class);

    BoardList toModel(BoardListDTO listDTO);
    BoardListDTO toDTO(BoardList list);
}