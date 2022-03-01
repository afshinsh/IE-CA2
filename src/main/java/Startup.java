import Main.Main;
import Model.Movie;
import Storage.Storage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.javalin.Javalin;

import java.util.List;

public class Startup {



    public static void main(String[] args) throws Exception {
        Javalin app = Javalin.create().start(7070);
        Main.RunCore();
        //app.get("/", ctx -> ctx.render("/200.html", model("name", "afshin", "age", 22)));
        HttpResponse <JsonNode> movieResponse = Unirest.get("http://138.197.181.131:5000/api/movies")
                .asJson();
        HttpResponse <JsonNode> actorResponse = Unirest.get("http://138.197.181.131:5000/api/actors")
                .asJson();
        HttpResponse <JsonNode> userResponse = Unirest.get("http://138.197.181.131:5000/api/users")
                .asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        Storage.Database.Movies =  objectMapper.readValue(movieResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.Actors =  objectMapper.readValue(actorResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.Users =  objectMapper.readValue(userResponse.getBody().toString(), new TypeReference<>(){});
        app.get("/", ctx -> ctx.result(String.valueOf(Storage.Database.Users.size())));
    }
}
