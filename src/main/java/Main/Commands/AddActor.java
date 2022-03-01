package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Actor;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@CMD(resource = "addActor")
public class AddActor {
    @EXC
    public void addActor(String JsonInput) {

        Object obj= JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try{
            Actor actor = new Actor(
                    Integer.valueOf(jsonObject.get("id").toString()),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("birthDate"),
                    (String) jsonObject.get("nationality")
            );
            Storage.Database.AddActor(actor);
            Response.CreateResponse(true, "actor added successfully");

        }
        catch (Exception e){
            Response.CreateResponse(false, "InvalidCommand");

        }


    }

}
