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

        HttpResponse<JsonNode> response = Unirest.post(BASE_URL+CARDS_ENDPOINT)
                .header("Accept", "application/json")
                .queryString("name", titulo)
                .queryString("desc", descricao)
                .queryString("idList", listID)
                .queryString("key", KEY)
                .queryString("token", TOKEN)
                .asJson();

        System.out.println(response.getBody());

    }

}
