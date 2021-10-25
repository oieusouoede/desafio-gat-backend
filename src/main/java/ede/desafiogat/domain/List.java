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
public class List {

    @Id
    private String listId;

    @Column
    private String listName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boardId")
    private Board board;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private java.util.List<Card> listCards;

}
