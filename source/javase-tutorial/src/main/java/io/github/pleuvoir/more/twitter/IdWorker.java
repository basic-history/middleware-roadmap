package io.github.pleuvoir.more.twitter;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * 
 * 64位，第一位0固定； 41bit时间戳 ，10bit工作机器id，12bit序列号
 * 
 * <p>
 * 其中41bit时间戳的值是：当前时间戳减去一个起始时间
 * 
 * 41bit最大值是41个1，二进制转换为long再转换为时间戳为2039-09-07
 * 23:47:35，所以如果我们直接使用当前时间戳，那么距离这个时间会很近，比如现在是2019年，20年后就不能使用了， n<=m n-i<=m
 * 往前推一段时间，那么当n当前时间为m+i时则到达理论最大值2084-09-06 15:47:35；
 * 为什么是41位：因为时间戳转为二进制就是41位的
 * 
 * <p>
 * 10bit工作机器id 我们把它拆分为2部分，其中5bit为数据中心，5bit为机器
 * 
 * <p>
 * 序列号有12个bit，当同一毫秒内并发时需要生成序列号，最大值为 二进制12个1=4095，所以支持一毫秒的并发为4095，如果超过则等待下一毫秒
 * 
 * 
 * 
 *  <b>41位时间戳，前面补了23个0，所以向左移22位（第一位固定0）；10位的工作机器，DATA_ID左移动17位，WOKER_ID左移12位</b>
 * @author pleuvoir
 * 
 */
public class IdWorker {

	// 起始时间
	private static final long snsEpoch = 1479692912967L;
	// 上次生成id的时间戳
	private long lastTimestamp = -1L;
	// 序列号（如果同一毫秒并发需要使用）
	private long lastSequence = 0L;
	// 数据中心
	private static final long DATA_ID = dataIdGen();
	// 机器
	private static final long WOKER_ID = workIdGen();


	public synchronized long nextId() {
		long timestamp = timeGen();

		// 不允许时钟回拨
		if (timestamp < this.lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		// 上一次请求的时间戳和此刻相等，需要生成序列号
		if (this.lastTimestamp == timestamp) {
			lastSequence = lastSequence + 1;
			if (lastSequence > 4095L) {
				timestamp = nextMill(lastTimestamp); // 下一毫秒
				lastSequence = 0L;
			}
		} else {
			lastSequence = 0L;
		}

		// 上次生成id的时间戳
		lastTimestamp = timestamp;

		// 时间部分
		long timePart = timestamp - snsEpoch;

		return (timePart << 22) | (DATA_ID << 17) | (WOKER_ID << 12) | lastSequence;
	}

	/**
	 * 获取当前毫秒数
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * 下一毫秒
	 */
	private long nextMill(long lastMill) {
		long cur = System.currentTimeMillis();
		while (lastMill <= cur) {
			cur = System.currentTimeMillis();
		}
		return cur;
	}

	/**
	 * 数据中心Id，使用hostname（机器名）取余，获得的值最大为5个bit的最大值11111 = 31
	 * 
	 */
	private static long dataIdGen() {
		try {
			return getHostId(Inet4Address.getLocalHost().getHostName(), 31);
		} catch (UnknownHostException e) {
			return ThreadLocalRandom.current().nextLong(32);
		}
	}

	/**
	 * 机器Id，使用hostadddress（IP地址）取余，获得的值最大为5个bit的最大值11111 = 31
	 * 
	 */
	private static long workIdGen() {
		try {
			return getHostId(Inet4Address.getLocalHost().getHostAddress(), 31);
		} catch (UnknownHostException e) {
			return ThreadLocalRandom.current().nextLong(32);
		}
	}

	/**
	 * 对字符串取余，获得的结果最大为max
	 */
	private static long getHostId(String value, int max) {
		byte[] bytes = value.getBytes();
		int sum = 0;
		for (byte b : bytes) {
			sum += b;
		}
		return sum % (max + 1);
	}

}
