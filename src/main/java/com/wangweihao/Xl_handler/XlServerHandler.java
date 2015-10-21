package com.wangweihao.Xl_handler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.util.Objects;
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
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("服务端收到:" + body);
        String s = "hello world\n";
        ByteBuf resp = Unpooled.copiedBuffer(req);
        byte[] bt = s.getBytes();
        ByteBuf rett = Unpooled.copiedBuffer(bt);
        ctx.write(rett);
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
