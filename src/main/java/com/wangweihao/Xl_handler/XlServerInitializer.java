package com.wangweihao.Xl_handler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by wwh on 15-10-18.
 */
public class XlServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception{
        ChannelPipeline pipeline = socketChannel.pipeline();
    }
}
