package io.github.pleuvoir.more.twitter;

import java.util.Date;

/**
 * <p>
 * 
 * 64位，第一位0固定； 41bit时间戳 ，10bit工作机器id，12bit序列号
 * 
 * <p>
 * 其中41bit时间戳的值是：当前时间戳减去一个起始时间
 * 
 * 41bit最大值是41个1，二进制转换为long再转换为时间戳为2039-09-07 23:47:35，所以如果我们直接使用当前时间戳，那么距离这个时间会很近，比如现在是2019年，20年后就不能使用了，
 * n<=m  n-i<=m 往前推一段时间，那么当n当前时间为m+i时则到达理论最大值2084-09-06 15:47:35
 * 
 * <p>
 * 
 * 
 * 
 * @author pleuvoir
 * 
 */
public class IdWorker {

	// 起始时间
	private static final long snsEpoch = 1479692912967L;
	// 上次生成id的时间戳
	private  long lastTimestamp = -1L;
	//序列号
	private  long sequence = 0L;

	public static void main(String[] args) {

		Date date = new Date(System.currentTimeMillis());
		System.out.println(date);

	}

	public long nextId() {
		long timestamp = timeGen();

		// 不允许时钟回拨
		if (timestamp < this.lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}
		
		//上一次请求的时间戳和此刻相等，需要生成序列号
		if(this.lastTimestamp == timestamp) {
			
		}else {
			sequence = 0L;
		}

		//上次生成id的时间戳
		lastTimestamp = timestamp;
				
		return 0;
	}

	/**
	 * 获取当前毫秒数
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}
}
