package tryCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by wwh on 16-2-16.
 */
public class XlClient {
    public static void main(String[] args) throws IOException {
        Socket socket  = new Socket("121.42.210.40", 10002);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        out.write("{\"mark\":1, \"account\":\"1111\"}\r\n".getBytes());
        byte[] b = new byte[1000];
        in.read(b);
        String s = new String(b);
        System.out.println(s);
    }
}
