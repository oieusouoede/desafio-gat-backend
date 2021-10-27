package ede.desafiogat.domain.models;

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
    @Column(nullable = false)
    private String cardId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private BoardList list;

    @Column
    private String cardName;

    @Lob
    @Column
    private String cardDesc;

}
