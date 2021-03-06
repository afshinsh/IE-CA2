import Controllers.MovieController;
import Controllers.UserController;
import Storage.Storage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import io.javalin.Javalin;

public class Startup {



    public static void main(String[] args) throws Exception {
        Javalin app = Javalin.create().start(7070);
        HttpResponse <JsonNode> movieResponse = Unirest.get("http://138.197.181.131:5000/api/movies")
                .asJson();
        HttpResponse <JsonNode> actorResponse = Unirest.get("http://138.197.181.131:5000/api/actors")
                .asJson();
        HttpResponse <JsonNode> userResponse = Unirest.get("http://138.197.181.131:5000/api/users")
                .asJson();
        HttpResponse <JsonNode> commentResponse = Unirest.get("http://138.197.181.131:5000/api/comments")
                .asJson();

        ObjectMapper objectMapper = new ObjectMapper();
        Storage.Database.Movies =  objectMapper.readValue(movieResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.SetRatingForMovies();
        Storage.Database.Actors =  objectMapper.readValue(actorResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.Users =  objectMapper.readValue(userResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.AssignIdToUsers();
        Storage.Database.Comments = objectMapper.readValue(commentResponse.getBody().toString(), new TypeReference<>(){});
        Storage.Database.AssignIdToCommnet();
        try{
            app.get("/", ctx -> ctx.html("<a href=\"http://localhost:7070/movies\">Movies</a>"));
            app.get("/movies", MovieController::GetAllMovie);
            app.get("/movies/{movie_id}", MovieController::GetMovieById);
            app.get("/rateMovie/{user_id}/{movie_id}/{rate}", MovieController::RateMovie);
            app.post("/rateMovie", MovieController::RateMovieFromMoviePage);
            app.get("/movies/search/{start_year}/{end_year}", MovieController::SearchMovieByYear);
            app.get("/watchList/{user_id}", UserController::GetWatchList);
            app.get("/watchList/{user_id}/{movie_id}", UserController::AddToWatchList);
            app.post("/addWatchList", UserController::AddToWatchList);
            app.post("/removeWatchList", UserController::RemoveFromWatchList);
            app.post("/like", MovieController::LikeAComment);
            app.post("/dislike", MovieController::Dislike);
            app.get("/movies/search/{genre}", MovieController::SearchByGenre);
            app.get("/voteComment/{user_id}/{comment_id}/{vote}", MovieController::VoteCommentFromUrl);

        }catch(Exception e){
            app.get("/Error", ctx -> ctx.result(e.getMessage()));
        }


    }
}
