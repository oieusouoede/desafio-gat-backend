package ede.desafiogat.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    private String emailId;

    @Column
    private String emailSender;

    @Column
    private String emailSubject;

    @Column
    private String emailMessage;

    @Column
    private Date emailDate;

    @OneToOne
    private Log log;

}
