package io.github.pleuvoir.juc.container.producerconsumer;

import java.util.concurrent.BlockingQueue;

public class SOSConsumer implements Runnable {

	private BlockingQueue<SOSData> queue;

	public SOSConsumer(BlockingQueue<SOSData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for (;;) {
			try {
				SOSData data = queue.take();
				System.out.println(Thread.currentThread().getName() + " 接收到求救信号：" + data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
