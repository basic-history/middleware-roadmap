package io.github.pleuvoir.juc.datasourcepool;

import java.sql.Connection;
import java.util.LinkedList;

public class DatasourcePool {

	private LinkedList<Connection> pool;

	// 连接池大小
	public DatasourcePool(int capacity) {
		// 初始化一定大小的连接到容器中
		if (capacity > 0) {
			pool = new LinkedList<>();
			for (int i = 0; i < capacity; i++) {
				pool.addLast(SQLConnectionImpl.fetchOneConnection());
			}
		}
	}

	/**
	 * 获取连接，超时后返回null
	 */
	public Connection get(long timeout) throws InterruptedException {
		synchronized (pool) {
			// 永不超时
			if (timeout < 0) {
				while (pool.isEmpty()) { // 当连接池为空时一直等待
					pool.wait();
				}
				return pool.removeFirst(); // 当其他线程释放连接时获取连接
			} else {
				long overtime = System.currentTimeMillis() + timeout;  //超时的那一刻
				long remain = timeout; //还能等待多久
				while (pool.isEmpty() && remain > 0) { // 当连接池为空并且未超过超时时间时一直等待
					pool.wait(remain);
					remain = overtime - System.currentTimeMillis(); //重置超时时间
				}
				// 过了超时时间池中有则返回，否则返回null
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}

	/**
	 * 归还数据库连接
	 */
	public void release(Connection connection) {
		if (connection != null) {
			synchronized (pool) {
				pool.addLast(connection);
				pool.notifyAll();
			}
		}
	}
}
