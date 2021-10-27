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
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "cardId")
    private Card card;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "emailId")
    private Email email;

    @Column
    private String logMessage;

    @Column
    private Long logDate;

}