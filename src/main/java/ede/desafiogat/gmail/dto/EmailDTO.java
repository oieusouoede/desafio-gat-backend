package ede.desafiogat.gmail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private String emailId;
    private String emailSender;
    private String emailSubject;
    private String emailMessage;
    private Date emailDate;
}
