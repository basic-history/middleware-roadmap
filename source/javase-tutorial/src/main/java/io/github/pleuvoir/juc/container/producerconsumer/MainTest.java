package io.github.pleuvoir.juc.container.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class MainTest {

	private static final int PRODUCER_THREAD_NUM = 10;
	private static final int CONSUMER_THREAD_NUM = 2;

	public static void main(String[] args) {

		BlockingQueue<SOSData> queue = new LinkedBlockingQueue<SOSData>(100);

		ExecutorService producerPool = Executors.newFixedThreadPool(PRODUCER_THREAD_NUM);
		ExecutorService consumerPool = Executors.newFixedThreadPool(CONSUMER_THREAD_NUM);

		for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
			producerPool.execute(new SOSProducer(queue));
		}

		for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
			consumerPool.execute(new SOSConsumer(queue));
		}

	}
}
