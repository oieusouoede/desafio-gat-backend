package ede.desafiogat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
    private List list;

    @Column
    private String cardName;

    @Column String cardDesc;

}
