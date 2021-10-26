package ede.desafiogat.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId", referencedColumnName = "cardId")
    private Card card;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emailId", referencedColumnName = "emailId")
    private Email email;

    @Column
    private String logMessage;

    @Column
    private Date logDate;

}