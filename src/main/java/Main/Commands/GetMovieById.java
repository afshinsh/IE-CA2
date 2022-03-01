package Main.Commands;

import Main.Interfaces.CMD;
import Main.Interfaces.EXC;
import Main.Response;
import Storage.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@CMD(resource = "getMovieById")
public class GetMovieById {
    @EXC
    public void getMovie(String JsonInput) throws JsonProcessingException {
        Object obj= JSONValue.parse(JsonInput);
        JSONObject jsonObject = (JSONObject) obj;
        int id = Integer.valueOf(jsonObject.get("movieId").toString());
        try{
            var result = Storage.Database.GetMovie(id);
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("{\"data\":" + objectMapper.writeValueAsString(result) + "}");

        }catch (Exception ex){
            Response.CreateResponse(false, "Invalid Command");
        }

    }
}
