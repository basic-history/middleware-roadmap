package io.github.pleuvoir.juc.container.delayorder;

import java.util.concurrent.DelayQueue;

public class GetOrderTask implements Runnable {

	private DelayQueue<DelayItem<Order>> queue;

	public GetOrderTask(DelayQueue<DelayItem<Order>> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		for (;;) {
			try {
				DelayItem<Order> item = queue.take();
				System.out.println("接收到订单：" + item.getData().getOrderNo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
