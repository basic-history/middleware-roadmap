package io.github.pleuvoir.juc.container.delayorder;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

	public static void main(String[] args) {

		DelayQueue<DelayItem<Order>> queue = new DelayQueue<>();

		ExecutorService producerPool = Executors.newFixedThreadPool(1);

		producerPool.execute(new PutOrderTask(queue));

		new Thread(new GetOrderTask(queue)).start();
	}
}
