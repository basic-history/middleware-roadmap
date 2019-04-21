package io.github.pleuvoir.netty.httpserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpServerCustomHandler extends ChannelInboundHandlerAdapter {

	// 连接建立的时候不能往浏览器写数据，测试无效
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("你已经和服务器建立连接了，你的地址是：" + ctx.channel().remoteAddress());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 因为启用了聚合
		FullHttpRequest httpRequest = (FullHttpRequest) msg;
		try {
			String uri = httpRequest.uri();
			String content = httpRequest.content().toString(CharsetUtil.UTF_8);
			HttpMethod method = httpRequest.method();
			HttpVersion protocolVersion = httpRequest.protocolVersion();
			HttpHeaders headers = httpRequest.headers();
			
			System.out.println("--------------------------");
			System.out.println("uri=" + uri);
			System.out.println("content=" + content);
			System.out.println("method=" + method);
			System.out.println("protocolVersion=" + protocolVersion);
			System.out.println("headers=" + headers);
			
			if ("/favicon.ico".equals(uri)) {
				send(ctx, "找不到favicon.ico", HttpResponseStatus.NOT_FOUND);
				return;
			}

			send(ctx, "说真的我只是一个简单的HTTP服务器", HttpResponseStatus.OK);
			
		} finally {
			httpRequest.release();
		}
	}
	
	// 发送响应
	private void send(ChannelHandlerContext ctx, String content, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
