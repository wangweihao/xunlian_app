package tryCode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by wwh on 15-11-22.
 */
public class Try {
    public static void main(String[] args){
        String  s = "{\"INFO\":[{\"name\":\"123\"}, {\"name\":\"123123\"}, {\"name\":\"123123123\"}]}";
        JSONObject js = new JSONObject(s);
        JSONArray ja = js.getJSONArray("INFO");
        for(int i = 0; i < ja.length(); ++i){
            JSONObject j = (JSONObject)ja.get(i);
            System.out.println(j.getString("name"));
        }
    }
}
