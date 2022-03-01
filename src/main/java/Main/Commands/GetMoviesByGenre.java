package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.WatchList;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


@CMD(resource = "getMoviesByGenre")
public class GetMoviesByGenre {
    @EXC
    public void getMoviesByGenre(String JsonInput) {
        Object obj = JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try {

            String genre = (String) jsonObject.get("genre");
            try {
                Storage.Database.GetMoviesByGenre(genre);
            }catch (Exception e){
                Response.CreateResponse(false, e.getMessage());

            }


        } catch (Exception e) {
            Response.CreateResponse(false, "InvalidCommand");
        }
    }
}

