package io.github.pleuvoir.more.twitter;

public class TestIdWoker {
	
	
	public static void main(String[] args) {
		
		
		
//		for(int i = 0;i<1;i++) {
//			long nextId2 = IdUtils.nextId();
//			System.out.println(nextId2);
//		}
		
		String s1 = "0100000000100000000000010110111110001110001000000011011110110110";
		String s2 = "0001000100000000000000010110011110001110001000000011011110110110";
		
		System.out.println(s1);
		System.out.println(s2);
		
		System.out.println(toBinaryString(toLong(s1) & toLong(s2)));  //求交集
		System.out.println(toBinaryString(toLong(s1) | toLong(s2))); //求合集
		System.out.println(toBinaryString(toLong(s1) ^ toLong(s2))); //求合集和交集的对称差集
		
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
