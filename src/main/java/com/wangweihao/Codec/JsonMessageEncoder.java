package com.wangweihao.Codec;

import com.wangweihao.Object.RecvBasicMessageObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by wwh on 15-10-21.
 */

/*基于json的编码器，将结果组装成json对象并返回给客户端*/
public class JsonMessageEncoder {
    public JsonMessageEncoder(RecvBasicMessageObject object){
        //将ServerRecvMessageObject组装成ByteBuf
        tryString = "hello world hahahah";
    }

    public ByteBuf getByteBuf(){
        return message;
    }

    public ByteBuf send(){
        byte[] b = tryString.getBytes();
        ByteBuf ret = Unpooled.copiedBuffer(b);
        return ret;
    }

    private ByteBuf message;
    private String tryString;
}
