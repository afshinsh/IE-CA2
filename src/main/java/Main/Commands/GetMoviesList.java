package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Movie;
import Storage.Storage;
import Views.MovieView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

@CMD(resource = "getMoviesList")
public class GetMoviesList {
    @EXC
    public void getMovieList(String JsonInput) {

        var MoviesList = new ArrayList<MovieView>();
        JSONArray arr = new JSONArray();
        JSONObject obj=new JSONObject();

        try{
            List<Movie> movies = Storage.Database.GetAllMovies();
            for(Movie mve : movies){
                MovieView view = new MovieView(mve);
                MoviesList.add(view);

            }
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("{\"data\":{\"MoviesList\": " + mapper.writeValueAsString(MoviesList) + "}}");

        }
        catch (Exception e){

            Response.CreateResponse(false, "InvalidCommand");

        }
    }
}
