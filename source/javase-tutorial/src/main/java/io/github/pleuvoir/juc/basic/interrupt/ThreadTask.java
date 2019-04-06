package io.github.pleuvoir.juc.basic.interrupt;

import java.util.concurrent.TimeUnit;

public class ThreadTask extends Thread {

	@Override
	public void run() {

		// 如果线程本身就是需要轮询执行的可以使用这种方式
		while (!isInterrupted()) {
			System.out.println("ThreadTask working...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				interrupt();
			}
		}

		// 如果本身只执行一次，那么在需要终中断的时候判定标志位
//		if (!isInterrupted()) {
//			System.out.println("RunnableTask working...");
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				interrupt();
//			}
//		}
	}

}
