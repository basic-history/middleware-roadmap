package io.github.pleuvoir.more.guava;

import com.google.common.hash.Hashing;

/**
 * @author pleuvoir
 * 
 */
public class ConsistHashTest {
	
	public static void main(String[] args) {
		
		//机器数量
		int buckets = 3;
		long input =6;
		
		
		int bucket = Hashing.consistentHash(input, buckets); // bucket 的范围在 0 ~ buckets 之间
		
		System.out.println(bucket);
				
	}

}
