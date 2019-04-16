package io.github.pleuvoir.juc.container.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SOSProducer implements Runnable {

	private BlockingQueue<SOSData> queue;

	public SOSProducer(BlockingQueue<SOSData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for (;;) {
			try {
				long waiting = ThreadLocalRandom.current().nextLong(5);
				TimeUnit.SECONDS.sleep(waiting);
				queue.put(new SOSData(System.currentTimeMillis()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
