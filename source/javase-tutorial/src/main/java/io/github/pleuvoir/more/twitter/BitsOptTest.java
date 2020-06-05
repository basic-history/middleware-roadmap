package io.github.pleuvoir.more.twitter;

/**
 * 位运算基础
 * <p>
 * 64位中，第一位代表正负 0 + 1 -
 * <p>
 * & 的作用是位与运算，都是1结果才是1，只要有一个0就是0，求交集    yu
 * | 的作用是位或运算，两个数对应的位上只要有一个是1就是1 ，求和集； huo
 * ^ 求合集和交集的对称差集   yihuo 只有1和0 才会是1
 * ~ 的作用是取反，所在的位 1变 0 0 变1  fei
 * @author pleuvoir
 * 
 */
public class BitsOptTest {

	public static void main(String[] args) {

		// 转换为二进制
		System.out.println("Long.MAX_VALUE 的二进制				" + toBinaryString(Long.MAX_VALUE));
		// 0111111111111111111111111111111111111111111111111111111111111111

		System.out.println("5L 的二进制					" + toBinaryString(5L));
		// 0000000000000000000000000000000000000000000000000000000000000101

		System.out.println("Long.MIN_VALUE 的二进制				" + toBinaryString(Long.MIN_VALUE));
		// 1000000000000000000000000000000000000000000000000000000000000000

		// 正数左移操作，移动后右边补零
		System.out.println("Long.MAX_VALUE << 1 的二进制			" + toBinaryString(Long.MAX_VALUE << 1));
		// 1111111111111111111111111111111111111111111111111111111111111110

		// 正数右移操作，移动后左边补零
		System.out.println("Long.MAX_VALUE >> 1 的二进制			" + toBinaryString(Long.MAX_VALUE >> 1));
		// 0011111111111111111111111111111111111111111111111111111111111111

		// 或，只要有1就变为1
		System.out.println(
				"Long.MAX_VALUE | Long.MIN_VALUE 的二进制		" + toBinaryString(Long.MAX_VALUE | Long.MIN_VALUE));
		// 1111111111111111111111111111111111111111111111111111111111111111 其实就是-1

		// 异或
		System.out.println(
				"Long.MAX_VALUE ^ Long.MIN_VALUE 的二进制		" + toBinaryString(Long.MAX_VALUE ^ Long.MIN_VALUE));
		// 1111111111111111111111111111111111111111111111111111111111111111 其实就是-1
		
		// 取反，1变0，0变1
		System.out.println("~Long.MAX_VALUE 的二进制				" + toBinaryString(~Long.MAX_VALUE));
		
		//时间戳是41位的
		System.out.println("时间戳 的二进制					" + toBinaryString(System.currentTimeMillis()));
		// 0000000000000000000000010110111101000111110101000111000001111101
		
		// 常用的技巧
		
		//求出n位二进制数能表示的最大整数的公式  ~(-1L << n)，这个n最大为63
		System.out.println("求出64位二进制数能表示的最大整数的公式			" + (~(-1L << 63)));
		// 9223372036854775807 其实就是Long.MAX_VALUE
		
		
		// ## 由于位与操作 遇1则1，所以全是1的二进制值 & 比它小的值则是 小的值，可以利用这个特性当 XX大于YY时归零
		
		// 7的二进制是 111  15的二进制是1111  
		System.out.println("15L&14L						" + (15L&14L));
		// 14L
		
		//归零
		System.out.println("15L&16L						" + (15L&16L));
		// 0L

		System.out.println("15L&17L						" + (15L&17L));
		// 1L
		
		// ## 可以看出，如果有一个需求 n=0，每次+1，大于15则归零，可以用  n = (n+1)&15L; 当n=14时返回15，当n=15时则归0，循环往复
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
