package tryCode;

/**
 * Created by wwh on 15-11-1.
 */
public class thread implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new thread());
        t.start();
        System.out.println("m1");
        t.join();
        System.out.println("m2");
    }

    public void run() {
        System.out.println("r1");
        System.out.println("r2");
    }
}
