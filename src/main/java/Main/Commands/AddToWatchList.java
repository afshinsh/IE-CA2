package Main.Commands;


import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Rate;
import Model.WatchList;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@CMD(resource = "addToWatchList")
public class AddToWatchList {
    @EXC
    public void addToWatchList(String JsonInput) {
        Object obj = JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            WatchList watchList = new WatchList(
                    (String) jsonObject.get("userEmail"),
                    Integer.valueOf(jsonObject.get("movieId").toString())
            );
            try{
                Storage.Database.AddWatchList(watchList);
                Response.CreateResponse(true, "movie added to watchlist successfully");

            }catch (Exception e){
                Response.CreateResponse(false, e.getMessage());

            }

        } catch (Exception e) {
            Response.CreateResponse(false, "InvalidCommand");
        }
    }
}

