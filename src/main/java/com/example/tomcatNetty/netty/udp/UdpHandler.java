package com.example.tomcatNetty.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;


public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf byteBuf = datagramPacket.copy().content();

        byte[] bytes = new byte[byteBuf.readableBytes()];

        byteBuf.readBytes(bytes);

        String str = new String(bytes,"UTF-8");

        System.out.println("服务端接受的消息 : " + str);

        channelHandlerContext.writeAndFlush("服务端接受的消息 : " + str);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--->连接的客户端地址:"+ctx.channel().remoteAddress());
        ctx.writeAndFlush("成功与服务端建立UDP连接！");
        super.channelActive(ctx);
    }
}
