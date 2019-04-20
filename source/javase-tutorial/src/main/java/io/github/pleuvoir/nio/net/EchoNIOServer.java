package io.github.pleuvoir.nio.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于NIO的服务端，客户端可使用 {@link io.github.pleuvoir.io.net.EchoClient} 进行测试
 *
 */
public class EchoNIOServer {
	
	public static void main(String[] args) throws IOException {
		serve(8000);
	}
	
	public static void serve(int port) throws IOException {
		//打开通道
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		//设置为非阻塞
		serverChannel.configureBlocking(false);
		ServerSocket ss = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		//绑定通道与本地端口
		ss.bind(address);
		Selector selector = Selector.open();
		//注册通道到选择器
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
		for (;;) {
			try {
				//阻塞轮询
				selector.select();
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}
			//当有事件时会到达此处
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if (key.isAcceptable()) { //检查事件是否是一个新的已经就绪可以被接受的连接
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						client.configureBlocking(false);
						//接受客户端并将它注册到选择器，并指定感兴趣的事件，这里是读写
						// 这里msg.duplicate()就是下面key.attachment()的内容
						client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
						System.out.println("Accepted connection from " + client);
					}
					if (key.isWritable()) { //检查套接字是否已经准备好写数据
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						while (buffer.hasRemaining()) { //将数据写到已连接的客户端
							if (client.write(buffer) == 0) {
								break;
							}
						}
						client.close(); //关闭连接
					}
				} catch (IOException ex) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException ignore) {
					}
				}
			}
		}
	}
}
