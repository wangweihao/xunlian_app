package com.wangweihao.Codec;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import com.wangweihao.Object.RecvBasicMessageObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by wwh on 15-10-21.
 */

/*基于json的编码器，将结果组装成json对象并返回给客户端*/
public class JsonMessageEncoder {
    private String responseString;
    private AccessDatabase accessDatabase;

    public JsonMessageEncoder(AccessDatabase object){
        //将ServerRecvMessageObject组装成ByteBuf
        accessDatabase = object;
        responseString = new String();

    }

    /* 编码 */
    public void Encoding(){
        responseString = accessDatabase.getResponseString();
        responseString += "\r\n";
    }

    /* 发送数据 */
    public ByteBuf send(){
        byte[] sendByteDate = responseString.getBytes();
        ByteBuf sendByteBufDate = Unpooled.copiedBuffer(sendByteDate);
        return sendByteBufDate;
    }
}
