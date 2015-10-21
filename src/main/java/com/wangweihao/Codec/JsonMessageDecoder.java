package com.wangweihao.Codec;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.Object.DecectAccountRegister;
import com.wangweihao.Object.MarkToClassObjectMap;
import com.wangweihao.Object.RecvBasicMessageObject;
import io.netty.buffer.ByteBuf;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Created by wwh on 15-10-21.
 */

/*基于json的解码器，将来可能换成protocol buffer等*/
public class JsonMessageDecoder {
    public JsonMessageDecoder(ByteBuf buffer){
        recvBuffer = buffer;
        mapObject = new MarkToClassObjectMap();
    }

    public RecvBasicMessageObject Decoding() throws Exception {
        //解码，并组装成对应的对象
        byte[] requestByte = new byte[recvBuffer.readableBytes()];
        recvBuffer.readBytes(requestByte);
        String requestString = new String(requestByte, "UTF-8");
        System.out.println("body:" + requestString);
        GetMarkAndAccount(requestString);
        return null;
    }

    public void GetMarkAndAccount(String requestStr) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JSONObject requestJson = new JSONObject(requestStr);
        decodeObject = new RecvBasicMessageObject(requestJson.getInt("mark"),
                requestJson.getString("account"));
        /*
         * 使用反射
         * 通过map将mark转换为类名
         * 动态加载类
         */
        Class messageObject = Class.forName(mapObject.Mapping(decodeObject.getMark()));
        /*
         * 通过反射将对象实例化为AccessDatabase对象，准备查询数据库
         * 注意反射时访问权限问题
         * */
        AccessDatabase accessDatabaseObject = (AccessDatabase) messageObject.newInstance();
        accessDatabaseObject.AccessXlDatabase();
        System.out.println(decodeObject.getMark() + " haha " + decodeObject.getAccount());
    }

    private ByteBuf recvBuffer;
    private RecvBasicMessageObject decodeObject;
    private MarkToClassObjectMap mapObject;
}
