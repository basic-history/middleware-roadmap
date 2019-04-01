package io.github.pleuvoir.juc.datasourcepool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class Worker implements Runnable {

	private DatasourcePool pool;

	private AtomicLong success;

	private AtomicLong fail;

	private int count;

	private CountDownLatch latch;

	public Worker(DatasourcePool pool, AtomicLong success, AtomicLong fail, int count, CountDownLatch latch) {
		this.pool = pool;
		this.success = success;
		this.fail = fail;
		this.count = count;
		this.latch = latch;
	}

	@Override
	public void run() {
		while (count > 0) {
			try {
				Connection connection = pool.get(1000); //1秒超时
				if (connection == null) {
					fail.incrementAndGet();
					System.out.println(Thread.currentThread().getName() + "获取连接失败。");
				} else {
					// 模拟业务操作
					try {
						connection.createStatement();
						connection.commit();
					} catch (SQLException e) {
						
					} finally { //归还连接
						pool.release(connection);
						success.incrementAndGet();
					}
					System.out.println(Thread.currentThread().getName() + "获取到连接。");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				this.count--;
			}
		}
		latch.countDown();
	}

}
