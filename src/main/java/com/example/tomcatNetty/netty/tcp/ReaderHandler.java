package com.example.tomcatNetty.netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ReaderHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("服务端接受的消息 : " + s);
        //服务端断开的条件
        if("quit".equals(s)){
            channelHandlerContext.writeAndFlush("see you");
            channelHandlerContext.close();
        }
        channelHandlerContext.writeAndFlush("服务端接受的消息 : " + s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--->连接的客户端地址:"+ctx.channel().remoteAddress());
        ctx.writeAndFlush("成功与服务端建立TCP连接！");
        super.channelActive(ctx);
    }
}
