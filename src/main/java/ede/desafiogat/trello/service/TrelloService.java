package ede.desafiogat.trello.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TrelloService {

    private static final String TRELLO_CREDENTIALS_PATH = "/trello-credentials.json";

    private static final String BASE_URL = "https://api.trello.com/";
    public static final String BOARDS_ENDPOINT = "1/boards/";
    public static final String LISTS_ENDPOINT = "1/lists/";
    public static final String CARDS_ENDPOINT = "1/cards/";

    @Autowired
    private final RestTemplate restTemplate;



    public void getTrelloAccess() throws IOException, ParseException, UnirestException {
        JSONParser parser = new JSONParser();

        InputStream is = TrelloService.class.getResourceAsStream(TRELLO_CREDENTIALS_PATH);

        Object obj = parser.parse(new InputStreamReader(is));
        JSONObject jsonCredentials = (JSONObject) obj;

        String API_KEY = (String) jsonCredentials.get("api_key");
        String USER_TOKEN = (String) jsonCredentials.get("user_token");


        createBoard(API_KEY, USER_TOKEN);
    }

    // Métodos pra implementar:

    // Create board (cria um board GAT)

    public void createBoard(String KEY, String TOKEN) throws UnirestException {
        HttpResponse<String> response = Unirest.post(BASE_URL+BOARDS_ENDPOINT)
                .queryString("name", "{GAT}")
                .queryString("key", KEY)
                .queryString("token", TOKEN)
                .asString();

        System.out.println(response.getBody());

    }

    // Get board for action (pra pegar o board e usar pra criar listas

    // Get list for action (pra pegar uma lista e criar os cards



// Se eu conseguir autenticar via api

//  String url = "https://trello.com/1/authorize?expiration=1day&name=Desafio+GAT&return_url=https://localhost:8080/callback&callback_method=postMessage&scope=read,write&&response_type=token&key=" + trelloKey;

//  triggerPopup(url);

//    private void triggerPopup(String url) {
//
//        System.out.println("Attempting to reach: " + url);
//
//        String systemOS = System.getProperty("os.name").toLowerCase();
//
//        try {
//            if(Desktop.isDesktopSupported()) {
//                Desktop desktop = Desktop.getDesktop();
//                desktop.browse(new URI(url));
//            } else {
//                Runtime runtime = Runtime.getRuntime();
//                if(systemOS.contains("mac")) {
//                    runtime.exec("open " + url);
//                }
//                else if(systemOS.contains("nix") || systemOS.contains("nux")) {
//                    runtime.exec("xdg-open " + url);
//                }
//                else
//                    System.out.println("I couldn't launch a browser in your OS :( #SadFace");
//            }
//        }
//        catch(IOException | URISyntaxException e) {
//            System.out.println("Error: "+ e.getMessage());
//        }
//    }
}
