package io.github.pleuvoir.juc.container.delayorder;

import java.util.concurrent.DelayQueue;

public class PutOrderTask implements Runnable {

	private DelayQueue<DelayItem<Order>> queue;

	public PutOrderTask(DelayQueue<DelayItem<Order>> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		Order _5sOrder = new Order();
		_5sOrder.setOrderNo("ZF5S");
		DelayItem<Order> _5s = new DelayItem<>(5000, _5sOrder);
		System.out.println("订单5秒后到期." + _5s.getData().getOrderNo());

		Order _8sOrder = new Order();
		_8sOrder.setOrderNo("ZF8S");
		DelayItem<Order> _8s = new DelayItem<>(8000, _8sOrder);
		System.out.println("订单8秒后到期." + _8s.getData().getOrderNo());

		queue.offer(_5s);
		queue.offer(_8s);
	}

}
