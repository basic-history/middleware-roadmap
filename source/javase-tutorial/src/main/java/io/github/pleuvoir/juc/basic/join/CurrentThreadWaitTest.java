package io.github.pleuvoir.juc.basic.join;

import java.io.IOException;

/**
 * 阻塞当前线程的另一种写法
 *
 */
public class CurrentThreadWaitTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		 Thread.currentThread().join();

//		JoinMe joinMe = new JoinMe();
//		synchronized (joinMe) {
//			joinMe.wait();
//		}
		
	//	System.in.read();
	}
}
