package io.github.pleuvoir.netty.eventloop;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * <ul>
 * <li>每一个EventLoop都是一个Thread，相同的eventLoop可能会被分配给多个channel。</li>
 * 
 * <li>所以在Channel整个生命周期内都只有一个线程（EventLoop）。EventLoop将负责处理一个Channel生命周期内的所有事件。channelHandle也就没有线程安全的问题了。</li>
 * 
 * <li>需要注意ThreadLocal 的使用，因为它可能会在多个channel间生效</li>
 * <ul>
 * 
 * <p>
 *  
 *  每一个eventLoop都有自己的队列，如果当前调用的线程是支撑这个EventLoop的线程时直接执行，否则加入队列，当下次处理它的事件时便会执行这些队列中的任务/事件。
 *  请尽可能的重用eventLoop.
 * <p>
 */
@SuppressWarnings("unused")
public class EventLoopTest {

	public static void main(String[] args) {
		
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
		
		int defaultThreadNum = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
		System.out.println("如果不设置则是默认线程数：" + defaultThreadNum);
		
		Iterator<EventExecutor> iterator = nioEventLoopGroup.iterator();
		
		//这里输出的数量其实就是EventLoop的个数
		while (iterator.hasNext()) {
			EventExecutor eventExecutor = (EventExecutor) iterator.next();
			System.out.println(eventExecutor);
		}
		
		/*
		 * EventExecutor  可以强转为 EventLoop
		 *  public EventLoop next() {
		 *       return (EventLoop) super.next();
		 *   }
		 */
		
		//1
		nioEventLoopGroup.next().schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread() + " 5s later do.");
			}
		}, 5, TimeUnit.SECONDS);

		//2 和上面等价
		nioEventLoopGroup.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread() + " nioEventLoopGroup 5s later do.");
			}
		}, 5, TimeUnit.SECONDS);
		
		// 表现和ScheduledExecutorService是一样的
		ScheduledFuture<?> scheduledFuture = nioEventLoopGroup.scheduleAtFixedRate(() -> {
			try {
				System.out.println("定时任务开始等待休息。 " + System.currentTimeMillis());
				TimeUnit.SECONDS.sleep(3);
				System.out.println("定时任务开始休息结束。 " + System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, 1, 2, TimeUnit.SECONDS);
		
		// 可以取消
		//scheduledFuture.cancel(true);
		
		//nioEventLoopGroup.shutdownGracefully();
		
	}
}
