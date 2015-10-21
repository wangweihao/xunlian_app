package com.wangweihao.Codec;

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

    public void GetMarkAndAccount(String requestStr){
        JSONObject requestJson = new JSONObject(requestStr);
        decodeObject = new RecvBasicMessageObject(requestJson.getInt("mark"),
                requestJson.getString("account"));
        System.out.println(decodeObject.getMark() + " haha " + decodeObject.getAccount());
    }

    private ByteBuf recvBuffer;
    private RecvBasicMessageObject decodeObject;
}
