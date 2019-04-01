package io.github.pleuvoir.juc.interrupt;

import java.util.concurrent.TimeUnit;

public class RunnableTask implements Runnable {

	@Override
	public void run() {

		// 如果线程本身就是需要轮询执行的可以使用这种方式
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println("RunnableTask working...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
				// 静态方法，是否中断状态，注意：此方法会重新将标记为 置为 false
//				System.out.println(Thread.interrupted());
//				Thread.currentThread().interrupt();
			}
		}

		// 如果本身只执行一次，那么在需要终中断的时候判定标志位
//		if (!Thread.currentThread().isInterrupted()) {
//			System.out.println("RunnableTask working...");
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				Thread.currentThread().interrupt();
//			}
//		}
	}

}
