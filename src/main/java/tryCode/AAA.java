package tryCode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wwh on 15-10-26.
 */
public class AAA {
    AAA(int _a, String _b, char _c){
        a = _a;
        b = _b;
        c = _c;
    }
    int a;
    String b;
    char c;

    public static void main(String[] args){
        AAA a = new AAA(10, "asd", 'A');
        AAA b = new AAA(10, "qwe", 'B');
        List<AAA> la = new LinkedList<AAA>();
        la.add(a);
        la.add(b);
    }
}


