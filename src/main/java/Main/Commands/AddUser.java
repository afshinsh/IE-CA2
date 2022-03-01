package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.User;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


@CMD(resource = "addUser")
public class AddUser {
    @EXC
    public void addUser(String JsonInput) {
        Object obj = JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            User user = new User(
                    (String) jsonObject.get("email"),
                    (String) jsonObject.get("password"),
                    (String) jsonObject.get("nickName"),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("birthDate")
            );
            Storage.Database.AddUser(user);
            Response.CreateResponse(true, "user added successfully");

        } catch (Exception e) {
            Response.CreateResponse(false, "InvalidCommand");
        }
    }
}
