package io.github.pleuvoir.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {

	private double x, y;

	static CountDownLatch latch = new CountDownLatch(22);

	static StampedLock sl = new StampedLock();

	public static void main(String[] args) throws InterruptedException {

		long start = System.currentTimeMillis();
		StampedLockTest stampedLockTest = new StampedLockTest();

		Runnable read = new Runnable() {
			@Override
			public void run() {
				stampedLockTest.read();
			}
		};
		Runnable write = new Runnable() {
			@Override
			public void run() {
				ThreadLocalRandom current = ThreadLocalRandom.current();
				stampedLockTest.write(current.nextDouble(), current.nextDouble());
			}
		};

		for (int i = 0; i < 20; i++) {
			new Thread(read).start();

			if (i % 10 == 0) {
				new Thread(write).start();
			}
		}

		latch.await();
		System.out.println("cost time=" + (System.currentTimeMillis() - start) / 1000 + "s");
	}

	void write(double deltaX, double deltaY) {
		long stamp = sl.writeLock(); // 这是一个排它锁
		try {
			x += deltaX;
			y += deltaY;
			TimeUnit.SECONDS.sleep(1);
			System.out.println(Thread.currentThread().getName() + " write");
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	double read() { // 只读方法
		// 尝试乐观读锁
		long stamp = sl.tryOptimisticRead();
		double currentX = x, currentY = y;
		// 如果校验失败则代表数据类修改了
		if (!sl.validate(stamp)) {
			// 锁升级为悲观锁（如果有其他线程正在写，则会等待直到获取读锁）
			stamp = sl.readLock();
			try {
				currentX = x;
				currentY = y;
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread().getName() + " 悲观读锁成功");
				latch.countDown();
				return Math.sqrt(currentX * currentX + currentY * currentY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sl.unlockRead(stamp);
			}
		}
		System.out.println(Thread.currentThread().getName() + " 乐观读锁成功");
		latch.countDown();
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}

}
