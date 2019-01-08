package com.example.tomcatNetty.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class HelloWordNetty {

    private static final int port = 8899;

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(10);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        socketChannel.pipeline().addLast("encoder",new StringEncoder());
                        // 以("\n")为结尾分割的 解码器
                        socketChannel.pipeline().addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        socketChannel.pipeline().addLast("decoder",new StringDecoder());
                        socketChannel.pipeline().addLast(new ReaderHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true);

        try {
            ChannelFuture future = bootstrap.bind(port).sync();

            if (future.isSuccess()){
                System.out.println("--->netty server start success");
            }

            showdown(future,bossGroup,workGroup);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showdown(final ChannelFuture future,final EventLoopGroup bossGroup,final EventLoopGroup workGroup){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{

            System.out.println("showdown netty");

            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }));
    }
}
