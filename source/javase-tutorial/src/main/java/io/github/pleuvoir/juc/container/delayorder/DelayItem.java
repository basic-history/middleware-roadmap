package io.github.pleuvoir.juc.container.delayorder;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟元素
 *
 */
public class DelayItem<T> implements Delayed {

	/**
	 * 到期时间（执行时间）单位纳秒
	 */
	private long executeTime;

	/**
	 * 数据
	 */
	private T data;

	// time是过期时长，也就是延迟多少毫秒 5*1000即为5秒
	public DelayItem(long delayTime, T data) {
		// 将传入的时长转为超时的时刻
		this.executeTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
		this.data = data;
	}

	// 按照剩余时间排序
	@Override
	public int compareTo(Delayed o) {
		long d = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return (d == 0) ? 0 : (d > 0 ? 1 : -1);
	}

	// 该方法返回还需要延时多少时间，单位为纳秒，所以设计的时候最好使用纳秒
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	public long getExecuteTime() {
		return executeTime;
	}

	public T getData() {
		return data;
	}

}
