package io.github.pleuvoir.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

	static ReentrantLock reentrantLock = new ReentrantLock();
	static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	static Lock readLock = readWriteLock.readLock();
	static Lock writeLock = readWriteLock.writeLock();

	static CountDownLatch latch = new CountDownLatch(20);

	public static void main(String[] args) throws InterruptedException {

		long start = System.currentTimeMillis();
		ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();

		Runnable read = new Runnable() {
			@Override
			public void run() {
				readWriteLockTest.read();
			}
		};
		Runnable write = new Runnable() {
			@Override
			public void run() {
				readWriteLockTest.write();
			}
		};

		for (int i = 0; i < 18; i++) {
			new Thread(read).start();
		}
		for (int i = 18; i < 20; i++) {
			new Thread(write).start();
		}

		latch.await();
		System.out.println("cost time=" + (System.currentTimeMillis() - start) / 1000 + "s");
	}

	private void read() {
		try {
			readLock.lock();
			TimeUnit.SECONDS.sleep(1);  //读操作耗时越多，读写锁性能越优秀
			System.out.println(Thread.currentThread().getName() + " read");
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}

	private void write() {
		try {
			writeLock.lock();
			TimeUnit.SECONDS.sleep(1);
			System.out.println(Thread.currentThread().getName() + " write");
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}

}
