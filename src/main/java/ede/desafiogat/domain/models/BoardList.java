package ede.desafiogat.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardList {

    @Id
    @Column(nullable = false)
    private String listId;

    @Column
    private String listName;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "boardId")
    private Board board;

    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Card> listCards;

}
