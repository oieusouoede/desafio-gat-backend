package ede.desafiogat.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    private String cardId;

    @OneToOne
    private Log log;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listId")
    private BoardList list;

    @Column
    private String cardName;

    @Column
    private String cardDesc;

}
