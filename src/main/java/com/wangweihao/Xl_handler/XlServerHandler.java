package com.wangweihao.Xl_handler;
import com.wangweihao.Codec.JsonMessageDecoder;
import com.wangweihao.Codec.JsonMessageEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.util.logging.Logger;

/**
 * Created by wwh on 15-10-18.
 */
public class XlServerHandler extends ChannelHandlerAdapter{
    private static final Logger logger = Logger.getLogger(
            XlServerHandler.class.getName()
    );

    XlServerHandler(){
        System.out.println("serverHandler创建");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(msg instanceof ByteBuf){
            JsonMessageDecoder decoder = new JsonMessageDecoder((ByteBuf)msg);
            //查询处理
            JsonMessageEncoder encoder = new JsonMessageEncoder(decoder.Decoding());
            ctx.write(encoder.send());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("用户连接");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("用户断开连接");
    }
}
