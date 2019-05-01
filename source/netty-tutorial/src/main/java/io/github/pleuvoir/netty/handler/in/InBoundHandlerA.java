package io.github.pleuvoir.netty.handler.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InBoundHandlerA extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerA: " + msg);
        
        ctx.pipeline().remove("in-B");
        ctx.fireChannelRead(msg); //调用下一个handler
    }
}
