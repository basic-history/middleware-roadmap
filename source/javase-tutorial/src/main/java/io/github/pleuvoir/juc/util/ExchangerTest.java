package io.github.pleuvoir.juc.util;

import java.util.concurrent.Exchanger;

/**
 * 用于两个线程之间交换数据
 * 
 * <p>两个线程可以通过 exchange 方法交换数据，如果第一个线程先执行 exchange 方法，他会一直等待第二个线程也执行 exchange 方法，
 * 当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。<p>
 */
public class ExchangerTest {


	public static void main(String[] args) {
		Exchanger<String> exchange = new Exchanger<String>();

		new Thread(new Runnable() {
			@Override
			public void run() {
				String salary = "1";
				try {
					System.out.println("我的工资是" + salary + "，你呢？" + exchange.exchange(salary));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				String salary = "2";
				try {
					System.out.println("我的工资是" + salary + "，你呢？" + exchange.exchange(salary));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
