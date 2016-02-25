package com.wangweihao.Xl_handler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * Created by wwh on 15-10-18.
 */
public class XlServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception{
        ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
        socketChannel.pipeline().addLast(
                new DelimiterBasedFrameDecoder(10240,
                        delimiter));
        socketChannel.pipeline().addLast(new XlServerHandler());
    }
}
