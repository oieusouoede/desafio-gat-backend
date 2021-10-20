package ede.desafiogat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class GmailController {

    @PostMapping("/Callback")
    @ResponseStatus(HttpStatus.CREATED)
    public void GetToken (@RequestBody String data){
        System.out.println(data);
    }

}
