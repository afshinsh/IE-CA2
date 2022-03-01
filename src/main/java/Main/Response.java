package Main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public Response(boolean success, String data){
        Success = success;
        Data = data;
    }
    public boolean Success;
    public String Data;
    public static void CreateResponse(boolean success, String data){
        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(System.out, new Response(success, data));
        }
        catch (Exception e){
            System.out.println("Response Creation Failed");
        }*/
        Map obj=new HashMap();
        obj.put("success", success);
        obj.put("data", data);
        System.out.println(JSONValue.toJSONString(obj));
    }

}
