package tryCode;

/**
 * Created by wwh on 15-10-21.
 */
public class aaa {
    public void func(){
        return;
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = Class.forName("tryCode.ccc");
        aaa b = (aaa) c.newInstance();
        b.func();
    }
}
