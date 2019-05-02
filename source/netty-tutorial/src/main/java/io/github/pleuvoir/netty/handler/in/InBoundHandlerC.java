package io.github.pleuvoir.netty.handler.in;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InBoundHandlerC extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBoundHandlerC: " + msg);

      //  ctx.channel().writeAndFlush(msg);  //注意这里，如果不这样 则无法进入下一个 out
        
        //找到上一个outbound,然后进行传播 ；这个代码跳过OutBoundHandler是因为OutBoundHandler都在后面。
        ctx.writeAndFlush(msg);
    }
}
