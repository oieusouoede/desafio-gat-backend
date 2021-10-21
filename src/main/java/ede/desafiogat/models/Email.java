package ede.desafiogat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    private String id;

    @Column
    private String sender;

    @Column
    private String subject;

    @Column
    private String message;

    @Column
    private Date messageDate;

}
