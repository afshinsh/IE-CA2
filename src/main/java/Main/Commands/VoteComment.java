package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Model.Comment;
import Model.Vote;
import Storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.*;

@CMD(resource = "voteComment")
public class VoteComment {
    @EXC
    public void voteComment(String JsonInput) {
        Object obj= JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;

        try{
            String userEmail = (String) jsonObject.get("userEmail");
            if(!Storage.Database.UserExists(userEmail)){
                Response.CreateResponse(false, "UserNotFound");
                return;
            }
            Integer commentId = Integer.valueOf(jsonObject.get("commentId").toString());
            if(!Storage.Database.CommentExists(commentId)){
                Response.CreateResponse(false, "CommentNotFound");
                return;
            }
            Integer voteValue = Integer.valueOf(jsonObject.get("vote").toString());
            if(Math.abs(voteValue) > 1){
                Response.CreateResponse(false, "InvalidVoteValue");
                return;
            }
            Vote vote = new Vote(userEmail, commentId, voteValue);
            Storage.Database.AddVote(vote);

        }
        catch (Exception e){
            Response.CreateResponse(false, "InvalidCommand");
        }

    }
}