package io.github.pleuvoir.juc.atomic.aba;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {

	static AtomicStampedReference<String> asr = new AtomicStampedReference<>("A", 0);

	public static void main(String[] args) throws InterruptedException {
		int oldStamp = asr.getStamp();
		String oldReference = asr.getReference();
		System.out.println("版本号=" + oldReference + "，当前变量值=" + oldStamp);

		Thread rightThread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + "当前变量值=" + oldReference + "当前版本戳=" + oldStamp
						+ "更新成功？" + asr.compareAndSet(oldReference, "B", oldStamp, oldStamp + 1));
			}
		});

		rightThread.start();
		rightThread.join(); // 等正确的执行完

		Thread errorThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
				int stamp = asr.getStamp();
				String reference = asr.getReference();
				
				System.out.println(Thread.currentThread().getName() + "当前变量值=" + reference + "当前版本戳="
						+ stamp + "更新成功？" + asr.compareAndSet(oldReference, "B", oldStamp, oldStamp + 1));

				
				// 这是正确的使用方式，上面的只是为了模拟失败才使用了一开始定义的旧的oldStamp
				System.out.println(Thread.currentThread().getName() + "当前变量值=" + reference + "当前版本戳="
						+ stamp + "更新成功？"
						+ asr.compareAndSet(reference, "B", stamp, stamp + 1));
			}
		});

		errorThread.start();
		errorThread.join();
	}

}
