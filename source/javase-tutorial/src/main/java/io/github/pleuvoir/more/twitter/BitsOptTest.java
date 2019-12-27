package io.github.pleuvoir.more.twitter;

/**
 * 位运算基础
 * <p>
 * 64位中，第一位代表正负 0 + 1 -
 * <p>
 * | 的作用是或运算，两个数对应的位上只要有一个是1就是1 ；~ 的作用是取反，所在的位 1变 0 0 变1
 * 
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

		// 取反，1变0，0变1
		System.out.println("~Long.MAX_VALUE 的二进制				" + toBinaryString(~Long.MAX_VALUE));

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
