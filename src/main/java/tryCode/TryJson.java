package tryCode;

import com.wangweihao.ContactType.MailContact;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wwh on 15-10-27.
 */
public class TryJson {
    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        Map<Integer, String> mp = new HashMap<Integer, String>();
        mp.put(1, "string");
        mp.put(2, "werty");
        String s = "haha";
        json.put("name", s);
        json.put("map", mp);
        jsonArray.put(json);
        jsonArray.put(json);
        System.out.println(json.toString());
        System.out.println(jsonArray.toString());
    }
}
