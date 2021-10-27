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
public class BoardList {

    @Id
    @Column(nullable = false)
    private String listId;

    @Column
    private String listName;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Board board;
}
