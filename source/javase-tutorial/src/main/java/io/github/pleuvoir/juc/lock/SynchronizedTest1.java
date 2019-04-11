package io.github.pleuvoir.juc.lock;

public class SynchronizedTest1 {

	static Object monitor = new Object();

	static int j = 0;

	public static void main(String[] args) throws InterruptedException {
		Task first = new Task();
		Task second = new Task();
		first.start();
		second.start();
		first.join();
		second.join();
		System.out.println(j);
	}

	public static class Task extends Thread {
		@Override
		public void run() {
			synchronized (monitor) {
				for (int i = 0; i < 1000; i++) {
					j++;
				}
			}
		}
	}

}
