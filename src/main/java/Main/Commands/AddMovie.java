package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Actor;
import Model.Movie;
import Storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

@CMD(resource = "addMovie")
public class AddMovie {
    @EXC
    public void addMovie(String JsonInput) {
        Object obj=JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;

        try{
            Movie movie = new Movie(
                    Integer.valueOf(jsonObject.get("id").toString()),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("summary"),
                    (String) jsonObject.get("releaseDate"),
                    (String) jsonObject.get("director"),
                    (ArrayList<String>) jsonObject.get("writers"),
                    (ArrayList<String>) jsonObject.get("genres"),
                    (ArrayList<Integer>)jsonObject.get("cast"),
                    (double) jsonObject.get("imdbRate"),
                    (long) jsonObject.get("duration"),
                    Integer.valueOf(jsonObject.get("ageLimit").toString())
            );
            try{
                Storage.Database.AddMovie(movie);
                Response.CreateResponse(true, "movie added successfully");
            }
            catch (Exception e){
                Response.CreateResponse(false, e.getMessage());

            }

        }
        catch (Exception e){
            Response.CreateResponse(false, "InvalidCommand");
        }
    }
}
