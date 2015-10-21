package com.wangweihao.Xl_server;
import com.wangweihao.Xl_db.DatabasePool;
import com.wangweihao.Xl_handler.XlServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.EventListener;

/**
 * Created by wwh on 15-10-18.
 */
public class XlServer {
    private String serverIp;
    private int serverPort;
    private DatabasePool dbPool;

    public XlServer(String ip, int port){
        serverIp = ip;
        serverPort = port;
        dbPool = new DatabasePool("root", "w13659218813", "127.0.0.1", "XL_db");
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();

            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1000)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new XlServerInitializer());

            ChannelFuture future = server.bind(serverIp, serverPort).sync();
            System.out.println("bind...");
            future.channel()
                    .closeFuture()
                    .sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /*单元测试服务端*/
    public static void main(String[] args) throws Exception {
        XlServer server = new XlServer("127.0.0.1", 10000);
        server.run();
    }
}
