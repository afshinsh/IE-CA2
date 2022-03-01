package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.WatchList;
import Storage.Storage;
import org.json.JSONObject;
import org.json.simple.JSONValue;


@CMD(resource = "getWatchList")
public class GetWatchList {
    @EXC
    public void rateMovie(String JsonInput) {
        Object obj = JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            String userEmail = (String) jsonObject.get("userEmail");
            try {
                Storage.Database.GetUserWatchList(userEmail);
            }catch (Exception e){
                Response.CreateResponse(false, e.getMessage());

            }


        } catch (Exception e) {
            Response.CreateResponse(false, "InvalidCommand");
        }
    }
}

