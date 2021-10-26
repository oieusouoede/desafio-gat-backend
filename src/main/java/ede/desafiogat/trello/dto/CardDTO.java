package ede.desafiogat.trello.dto;

import ede.desafiogat.domain.models.BoardList;
import ede.desafiogat.domain.models.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private String cardId;
    private Log log;
    private BoardList list;
    private String cardName;
    private String cardDesc;
}
