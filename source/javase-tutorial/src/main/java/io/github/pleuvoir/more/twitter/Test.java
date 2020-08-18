package io.github.pleuvoir.more.twitter;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 * @author pleuvoir
 * 
 */
public class Test {

	public static void main(String[] args) throws InterruptedException, ParseException {

//		Integer count = 0;
//		for (;;) {
//			long currentTimeMillis = System.currentTimeMillis();
//			if (currentTimeMillis % 16 == 1) {
//				System.out.println(toBinaryString(currentTimeMillis));
//				count++;
//			}
//			if (count == 10) {
//				break;
//			}
//		}
		
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000
//		0000000000000000000000010111001011110101011011001100011101110000

//		Date date = new Date(currentTimeMillis);
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//		
//		long maxTimeMillis = toLong("111111111111");
//		System.out.println(maxTimeMillis);
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(maxTimeMillis)));
//		
//		long delta = maxTimeMillis - currentTimeMillis;
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(delta)));
//		
//		
//		
//		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1420041600000L + maxTimeMillis)));
//		
//		
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(toBinaryString(0x7FFFFFFF)); //0000000000000000000000000000000001111111111111111111111111111111
//		System.out.println(toBinaryString(127L));
		
//		
//		System.out.println(toBinaryString(1L<<41));
//		System.out.println((1L<<41));
//		System.out.println(((1L << 41) / (365 * 24 * 60 * 60 * 1000L)));
//		
//		
//		//返回从 1970-01-01 00:00:00 到现在过去的毫秒数
//		long currentTimeMillis = System.currentTimeMillis();
//		
//		
//		
//		
//		// 我们的算法中需要使用当前时间戳做为这41位的值，那么41位得到它的最大值是
//
//		long maxTimestamp = toLong(StringUtils.repeat("1", 41));
//		System.out.println(maxTimestamp);
//		System.out.println((1L << 41) - 1);
//		//2199023255551
//		
//		//那这个数字 从0递增过去需要多久呢？没错是 69 年，但是这个0指的可是1970年哦，现在可是2020年，也就是说50年已经没了，只剩下19年了
//		System.out.println(maxTimestamp/ oneYearMs);
//		
//		
//		//我们来计算一下
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		formatter.setTimeZone(TimeZone.getTimeZone("GMT")); //设置为格林威治时间
//		long begin1970Timestamp =  formatter.parse("1970-01-01 00:00:00 000").getTime();
//		System.out.println("beginTimestamp : " + begin1970Timestamp); // 时间戳为 beginTimestamp=0 
//		
//		long remainYear = (maxTimestamp - begin1970Timestamp) / oneYearMs;
//		System.out.println(remainYear); // 69
//		System.out.println(111);
//		
//		//这里来验证一下
//		// currentTimeMillis --> maxTimestamp 如果使用当前时间戳直接做为这41位的值，那么你想想看还能用多久？
//		
//		 remainYear = (maxTimestamp - currentTimeMillis) / oneYearMs;
//		System.out.println(remainYear); // 19
		
		//也就是说，如果从此刻开始算可以使用19年，那如果我们不从 1970年开始算呢？比如我们从 2015-01-01 00:00:00 开始算基准时间
		
//		//得到2015年到1970年过去的毫秒数
//		long begin2015Timestamp =  formatter.parse("2015-01-01 00:00:00 000").getTime();
//		
//		System.out.println("begin2015Timestamp : " + begin2015Timestamp); // 时间戳为 begin2015Timestamp=0 
//		
//		long remainYear2 = (maxTimestamp - begin2015Timestamp) / oneYearMs;
//		
//		
//		
//		long time = new Date(Long.MAX_VALUE).getTime();
//		System.out.println(toBinaryString(time));
//		
//		
//		long test = (1<<41) + 1L;
//		System.out.println(test);
		
		//42位的最小值
		long maxTimestamp = (1L << 41) + 1;
		
		
		//41位的最大值 0000000000000000000000011111111111111111111111111111111111111111
		System.out.println(toBinaryString((1L << 41) -1L)); 
				
		//42位的最小值 0000000000000000000000100000000000000000000000000000000000000000
		System.out.println(toBinaryString((1L << 41))); 
		
		//42位的最小值 0000000000000000000000100000000000000000000000000000000000000000
		System.out.println(toBinaryString((1L << 41) + 2L)); 
		
		
		
		Date date = new Date(toLong(StringUtils.repeat("1", 42)));
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		
		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		long begin2015Timestamp =  formatter.parse("2015-01-01 00:00:00 000").getTime();
//		
//		//一年有这么多毫秒
//		long oneYearMs = 365 * 24 * 60 * 60 * 1000L;
//				
//		//验证一下 执行这段代码的时间是2020年，得到的结果是50
//		System.out.println((maxTimestamp -  begin2015Timestamp)/ oneYearMs);
		
		
		System.out.println((1^2));
		System.out.println((2^2));
		System.out.println( (3^2));
		System.out.println( (4^2));
		System.out.println((5^2));
		
		
		
		
	}

	/**
	 * 获取下一不同毫秒的时间戳，不能与最后的时间戳一样
	 */
	public static long nextMillis(long lastMillis) {
		long now = System.currentTimeMillis();
		while (now <= lastMillis) {
			now = System.currentTimeMillis();
		}
		return now;
	}

	/**
	 * 转换为二进制，并对其补零，直到达到64位
	 */
	public static String toBinaryString(long value) {
		String binaryLong = Long.toBinaryString(value);
		while (binaryLong.length() < 64) {
			binaryLong = "0" + binaryLong;
		}
		return binaryLong;
	}

	/**
	 * 二进制转为数值
	 */
	public static long toLong(String binaryString) {
		return Long.valueOf(binaryString, 2); // 2代表原来的进制
	}

}
