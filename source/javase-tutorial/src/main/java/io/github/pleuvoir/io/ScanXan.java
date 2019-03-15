package io.github.pleuvoir.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 
 * 扫描和格式化
 *
 */
public class ScanXan {

	public static void main(String[] args) throws FileNotFoundException {

		scanRead();
		System.out.println(scanSumDouble()); // 1.0
		print();
	}


	//扫描
	private static void scanRead() throws FileNotFoundException {
		Scanner s = null;
		try {
			s = new Scanner(new BufferedReader(new FileReader(CopyBytes.filepath + "xanadu.txt")));
			while (s.hasNext()) {
				System.out.println(s.next());
			}
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	private static double scanSumDouble() throws FileNotFoundException {
		Scanner s = null;
		double sum = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(CopyBytes.filepath + "usnumbers.txt"));

			s = new Scanner(br);
			while (s.hasNext()) {
				sum += s.nextDouble();
			}
		} finally {

			if (s != null) {
				s.close();
			}
		}
		return sum;
	}
    // 扫描EOF
	
	
	
	//格式化
	private static void print() {
		
		System.out.print(1);
		System.out.println(10);

		int i = 2;
		double r = Math.sqrt(i);

		// https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#syntax
		System.out.format("%d的平方根是%f。%n", i, r);
		
		System.out.print("画在掌心的蝴蝶飞走了吗？\n");
	        
	}
	
	//格式化EOF

}
