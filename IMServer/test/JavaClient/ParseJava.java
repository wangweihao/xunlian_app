import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by wwh on 15-12-22.
 */
public class ParseJava {
    public static void main(String[] args) throws IOException, InterruptedException {
        /* 构建一个 build */
        Message.Login.Builder MsgBuild = Message.Login.newBuilder();
        MsgBuild.setId(1);
        MsgBuild.setSelfaccount("wangweihao");

        /* 生成一个实体消息 */
        Message.Login msg = MsgBuild.build();

        /* 将消息提取出来 */
        byte[] message = msg.toByteArray();
        System.out.println(message);
        System.out.println(message.length);

        /* 建立要通过 tcp 发送的消息 */
        /* 1字节version + 1字节mark + message.length(protocolbuffer)*/
        int lengthByte = 1 + 1 + message.length;
        byte[] sendMsg = new byte[lengthByte];

        /* 数据总长度 */
        short length = (short) ((1+1+message.length));
        System.out.println("lengthbit:" + length);
        /* version */
        sendMsg[0] = 1;
        /* mark */
        sendMsg[1] = 1;
        /* 拷贝 protocol buffer data */
        System.arraycopy(message, 0, sendMsg, 2, message.length);

        /* 创建 Socket */
        Socket socket = new Socket("127.0.0.1", 9090);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        /* 发送 2 字节长度，再发送实体消息 */
        out.writeShort(length);
        out.write(sendMsg);
    }
}
