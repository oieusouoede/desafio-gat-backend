package ede.desafiogat.trello.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
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

    private JSONParser parser;

    public void getTrelloAccess() throws IOException, ParseException, UnirestException {


        InputStream is = TrelloService.class.getResourceAsStream(TRELLO_CREDENTIALS_PATH);

        Object obj = parser.parse(new InputStreamReader(is));
        JSONObject jsonCredentials = (JSONObject) obj;

        String API_KEY = (String) jsonCredentials.get("api_key");
        String USER_TOKEN = (String) jsonCredentials.get("user_token");

        String boardName = "OH BOY";

        createBoard(API_KEY, USER_TOKEN, boardName);

    }

    public void createBoard(String KEY, String TOKEN, String name) throws UnirestException, ParseException {
        HttpResponse<String> response = Unirest.post(BASE_URL+BOARDS_ENDPOINT)
                .queryString("name", name)
                .queryString("defaultLists", "false")
                .queryString("key", KEY)
                .queryString("token", TOKEN)
                .asString();

        Object res = parser.parse(response.getBody());
        JSONObject jsonresp = (JSONObject) res;
        String boardID = (String) jsonresp.get("id");
        createList(KEY, TOKEN, boardID);

    }

    public void createList (String KEY, String TOKEN, String boardID) throws UnirestException, ParseException {
        HttpResponse<String> response = Unirest.post(BASE_URL+LISTS_ENDPOINT)
                .queryString("name", "Emails recebidos")
                .queryString("idBoard", boardID)
                .queryString("key", KEY)
                .queryString("token", TOKEN)
                .asString();

        System.out.println(response.getBody());

        Object res = parser.parse(response.getBody());
        JSONObject jsonresp = (JSONObject) res;

        // Daqui eu consigo pegar os detalhes da lista
        String mailListID = (String) jsonresp.get("id");
        createCard(KEY, TOKEN, mailListID);

    }

    public void createCard (String KEY, String TOKEN, String listID) throws UnirestException, ParseException {
        String titulo = "Assunto do email";
        String descricao = "Mussum Ipsum, cacilds vidis litro abertis. Si u mundo tá muito paradis? " +
                "Toma um mé que o mundo vai girarzis! Quem manda na minha terra sou euzis! Quem num gosta " +
                "di mim que vai caçá sua turmis! Mauris nec dolor in eros commodo tempor. Aenean aliquam " +
                "molestie leo, vitae iaculis nisl.";

        HttpResponse<JsonNode> response = Unirest.post("https://api.trello.com/1/cards")
                .header("Accept", "application/json")
                .queryString("name", titulo)
                .queryString("desc", descricao)
                .queryString("idList", listID)
                .queryString("key", KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());


    }








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
