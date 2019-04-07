package io.github.pleuvoir.juc.datasourcepool.better;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 这里实现比较难理解的是为什么有2个Semaphore？原因是因为数据库连接是个资源，空位本身也是个资源（空位本身也有申请和释放的一些动作）。
 * 方便我们统计和计算有多少连接被占用，又有多少空闲。当然我们使用一个Semaphore也是可以实现连接池的功能的。
 *
 */
public class BetterDatasourcePool {

	private Semaphore unuse, used;  // unuse 表示可用的数据库连接，used表示已用的数据库连接   unuse+used=capacity
	
	private LinkedList<Connection> pool;

	// 连接池大小
	public BetterDatasourcePool(int capacity) { 
		unuse = new Semaphore(capacity);
		used = new Semaphore(0);    //unuse+used=capacity
		// 初始化一定大小的连接到容器中
		if (capacity > 0) {
			pool = new LinkedList<>();
			for (int i = 0; i < capacity; i++) {
				pool.addLast(ConnectionDriver.fetchOneConnection());
			}
		}
	}

	/**
	 * 获取连接，超时后返回null
	 */
	public Connection get(long timeout) throws InterruptedException {
		Connection conn = null;
		if (unuse.tryAcquire(timeout, TimeUnit.MILLISECONDS)) {
			// 可用的-1
			synchronized (pool) {
				conn = pool.removeFirst();
			}
			used.release(); // 不可用的+1
		}
		return conn;
	}

	/**
	 * 归还数据库连接
	 * @throws InterruptedException 
	 */
	public void release(Connection connection) throws InterruptedException {
		if (connection != null) {
			//不可用的-1
			used.acquire();
			synchronized (pool) {
				pool.addLast(connection);
			}
			//可用的+1
			unuse.release();
		}
	}
}
