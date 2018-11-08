package com.aifec.netty.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author 洪峰
 * @create 2018-11-08 19:33
 **/

public class NettyServer {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 6666;

    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final int BIZTHREADSIZE = 100;

    private static final EventLoopGroup boosGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(BIZTHREADSIZE);
    
    public static void start() throws Exception{

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel channel) throws Exception {

                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("code",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        pipeline.addLast("decode",new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encode",new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast("handler",new TcpServerHandler());

                    }
                });

        ChannelFuture future = serverBootstrap.bind(IP,PORT).sync();

        future.channel().closeFuture().sync();
        System.out.println("server start");


    }

    private static void shutdown(){
        workGroup.shutdownGracefully();
        boosGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("启动Server");
        new NettyServer().start();
    }
    
}
