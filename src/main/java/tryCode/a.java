package tryCode;

import com.wangweihao.ContactType.Contact;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by wwh on 15-10-26.
 */
public class a {
    int i;
    public void func(){
        System.out.println("a");
    }

    public static void main(String[] args){
        TreeMap<Integer, a> hm = new TreeMap<Integer, a>();
        a a1 = new a();
        b b1 = new b();
        hm.put(1, a1);
        hm.put(2, b1);
        System.out.println(hm.get(3));
        System.out.println(hm.get(2));
        System.out.println(hm.get(1));
    }
}

class b extends a{
    public void func(){
        System.out.println("b");
    }
}

class c extends a{
    public void func(){
        System.out.println("c");;
    }
}
