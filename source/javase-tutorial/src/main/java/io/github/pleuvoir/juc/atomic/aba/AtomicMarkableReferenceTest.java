package io.github.pleuvoir.juc.atomic.aba;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceTest {

	static AtomicMarkableReference<String> amr = new AtomicMarkableReference<String>("A", false);

	public static void main(String[] args) throws InterruptedException {

		String oldReference = amr.getReference();

		Thread rightThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "当前变量值=" + oldReference + "更新成功？"
						+ amr.compareAndSet(oldReference, "B", false, true));
			}
		});
		
		Thread errorThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "当前变量值=" + oldReference + "更新成功？"
						+ amr.compareAndSet(oldReference, "B", false, true));
			}
		});
		

		rightThread.start();
		rightThread.join();
		
		errorThread.start();
		errorThread.join();
	}

}
