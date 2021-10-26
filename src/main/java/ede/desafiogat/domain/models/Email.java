package ede.desafiogat.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @Column(nullable = false)
    private String emailId;

    @Column
    private String emailSender;

    @Column
    private String emailSubject;

    @Lob
    @Column
    private String emailMessage;

    @Column
    private Date emailDate;

}
