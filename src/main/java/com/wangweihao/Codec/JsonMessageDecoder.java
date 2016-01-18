package com.wangweihao.Codec;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.Object.MarkToClassObjectMap;
import com.wangweihao.Object.RecvBasicMessageObject;
import io.netty.buffer.ByteBuf;
import org.json.JSONObject;


/**
 * Created by wwh on 15-10-21.
 */

/*基于json的解码器，将来可能换成protocol buffer等*/
public class JsonMessageDecoder {
    public JsonMessageDecoder(ByteBuf buffer){
        recvBuffer = buffer;
        mapObject = new MarkToClassObjectMap();
    }

    public AccessDatabase Decoding() throws Exception {
        //解码，并组装成对应的对象
        byte[] requestByte = new byte[recvBuffer.readableBytes()];
        recvBuffer.readBytes(requestByte);
        String requestString = new String(requestByte, "UTF-8");
        System.out.println("body:" + requestString);
        requestStr = requestString;
        return GetMarkAndAccount();
    }

    public AccessDatabase GetMarkAndAccount() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        JSONObject requestJson = new JSONObject(requestStr);
        decodeObject = new RecvBasicMessageObject(requestJson.getInt("mark"),
                requestJson.getString("account"));
        /*
         * 使用反射
         * 通过map将mark转换为类名
         * 通过反射将对象实例化为AccessDatabase对象
         * 注意反射时访问权限问题
         */
        Class messageObject = Class.forName(mapObject.Mapping(decodeObject.getMark()));
        System.out.println(mapObject.Mapping(decodeObject.getMark()));
        System.out.println("实例化类");
        AccessDatabase accessDatabaseObject = (AccessDatabase) messageObject.newInstance();
        System.out.println("实例化完毕");
        accessDatabaseObject.setRequestString(requestStr);
        accessDatabaseObject.basicObject.setValue(decodeObject);
        System.out.println("requestStr:" + requestStr);
        return accessDatabaseObject;
    }

    private ByteBuf recvBuffer;
    private RecvBasicMessageObject decodeObject;
    private MarkToClassObjectMap mapObject;
    private String requestStr;
}
