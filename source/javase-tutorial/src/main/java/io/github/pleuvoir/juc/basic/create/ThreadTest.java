package io.github.pleuvoir.juc.basic.create;

public class ThreadTest extends Thread {

	@Override
	public void run() {
		System.out.println("你好Thread");
	}

	public static void main(String[] args) {
		new ThreadTest().start();
	}
}
