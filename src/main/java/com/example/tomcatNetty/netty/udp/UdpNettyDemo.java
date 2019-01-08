package com.example.tomcatNetty.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpNettyDemo {

    private static final int port = 9900;

    public void run(){
        EventLoopGroup workGroup = new NioEventLoopGroup(10);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new UdpHandler());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            if (future.isSuccess()){
                System.out.println("UDP started success");
            }

            showdown(future,workGroup);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showdown(final ChannelFuture future,final EventLoopGroup workGroup){
        Runtime.getRuntime().addShutdownHook(new Thread(()->{

            System.out.println("showdown netty");

            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workGroup.shutdownGracefully();
        }));
    }
}
