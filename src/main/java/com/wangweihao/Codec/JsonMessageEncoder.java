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
        responseString = "name:" + object.getAccount();
    }

    public ByteBuf send(){
        byte[] b = responseString.getBytes();
        ByteBuf ret = Unpooled.copiedBuffer(b);
        return ret;
    }

    private String responseString;
}
