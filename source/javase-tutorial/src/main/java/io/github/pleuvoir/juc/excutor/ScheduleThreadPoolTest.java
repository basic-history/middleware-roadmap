package io.github.pleuvoir.juc.excutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduleThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3, new ThreadFactory() {
			AtomicLong count = new AtomicLong(1);

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("nameThreadFactory-" + count.getAndIncrement());
				return thread;
			}
		});

		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " time：" + System.currentTimeMillis() + " go");
			}
		};
		
		Runnable scheduleAtFixedRateTask = new Runnable() {
			@Override
			public void run() {
				System.out.println("scheduleAtFixedRateTask" + " begin time：" + System.currentTimeMillis() + " go");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("scheduleAtFixedRateTask" + " end   time：" + System.currentTimeMillis() + " go");
			}
		};
		
		// 1秒过后执行
		scheduledThreadPool.schedule(task, 1, TimeUnit.SECONDS);

		// 1秒以后开始启动，每过两秒执行一次，如果上一个工作超过了2秒，那么下一个并不会开始，而是等到上一个结束后立马开始。
		scheduledThreadPool.scheduleAtFixedRate(scheduleAtFixedRateTask, 1, 2, TimeUnit.SECONDS);

		// 1秒以后开始启动，每个任务之间间隔2秒，无论上一个任务执行了多久，下一个任务总是会在上一个任务结束后2秒开始
		scheduledThreadPool.scheduleWithFixedDelay(task, 1, 2, TimeUnit.SECONDS);
		
		// 结束后，两个定时执行的都无法工作
		// scheduledThreadPool.shutdown();
	}
}
