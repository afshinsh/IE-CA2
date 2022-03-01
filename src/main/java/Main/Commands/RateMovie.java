package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Rate;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@CMD(resource = "rateMovie")
public class RateMovie {
    @EXC
    public void rateMovie(String JsonInput) {
        Object obj = JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            Rate rate = new Rate(
                    (String) jsonObject.get("userEmail"),
                    Integer.valueOf(jsonObject.get("movieId").toString()),
                    Integer.valueOf(jsonObject.get("score").toString())
            );

            try {
                Storage.Database.AddRateMovie(rate);
                Response.CreateResponse(true, "movie rated successfully");
            }catch (Exception e){
                Response.CreateResponse(false, e.getMessage());

            }


        } catch (Exception e) {
            Response.CreateResponse(false, "InvalidCommand");

        }
    }
}
