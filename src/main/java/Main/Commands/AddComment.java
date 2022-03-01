package Main.Commands;

import Main.Command;
import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Comment;
import Model.Movie;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

@CMD(resource = "addComment")
public class AddComment {
    @EXC
    public void addComment(String JsonInput) {
        Object obj= JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;

        try{
            Comment comment = new Comment(
                    (String) jsonObject.get("userEmail"),
                    Integer.valueOf(jsonObject.get("movieId").toString()),
                    (String) jsonObject.get("text")
            );
            Storage.Database.AddComment(comment);
        }
        catch (Exception e){
            Response.CreateResponse(false, "InvalidCommand");
        }

    }
}
