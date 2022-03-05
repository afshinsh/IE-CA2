
import Controllers.MovieController;
import Model.Rate;
import Model.WatchList;
import Storage.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.javalin.http.Context;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCA2 {

    @Before
    public void assignDatas() throws UnirestException, JsonProcessingException {
        HttpResponse<JsonNode> movieResponse = Unirest.get("http://138.197.181.131:5000/api/movies")
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
    }


    @Test
    public void testRateMovieSuccess() {
        try {
            var before = Storage.Database.GetNumOfRates(1);
            Rate rating = new Rate("sara@ut.ac.ir", 1, 8);
            Storage.Database.AddRateMovie(rating);
            var after = Storage.Database.GetNumOfRates(1);
            assertEquals(after - before, 1);
        }
        catch (Exception e){
        }
    }
    @Test
    public void testRateMovieFailure() {
        try {
            Rate rating = new Rate("sara@ut.ac.ir", 1, 21);
            Storage.Database.AddRateMovie(rating);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "InvalidRateScore");
        }
    }

    @Test
    public void testGetMoviesByYear() {
        try {
            var movies = Storage.Database.GetMovieByYear(2000, 2020);
            boolean success = true;
            for (var mve : movies){
                var year = mve.releaseDate.substring(0, 4);
                if (Integer.valueOf(year) > 2020 || Integer.valueOf(year) < 2000)
                    success = false;
            }
            assertEquals(success, true);
        }
        catch (Exception e){
            assertEquals(e.getMessage(), "InvalidRateScore");
        }
    }

    @Test
    public void testWatchList() throws Exception {
        var before = Storage.Database.getUserById(1).watchList.size();
        Storage.Database.AddWatchList(new WatchList("sara@ut.ac.ir", 1));
        var after = Storage.Database.getUserById(1).watchList.size();
        assertEquals(after - before , 1);

    }

}
