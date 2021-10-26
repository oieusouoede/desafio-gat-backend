package ede.desafiogat.trello.dto;

import ede.desafiogat.domain.models.BoardList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String cardId;
    private BoardListDTO list;
    private String cardName;
    private String cardDesc;
}
