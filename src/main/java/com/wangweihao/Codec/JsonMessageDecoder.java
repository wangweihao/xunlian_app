package com.wangweihao.Codec;

import com.wangweihao.Object.ServerRecvMessageObject;
import io.netty.buffer.ByteBuf;

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

    public ServerRecvMessageObject Decoding() throws Exception {
        //解码，并组装成对应的对象
        String body = new String(new byte[recvBuffer.readableBytes()], "UTF-8");
        System.out.println("----------------------" + body);

        return null;
    }

    private ByteBuf recvBuffer;
    private ServerRecvMessageObject decodeObject;
}
