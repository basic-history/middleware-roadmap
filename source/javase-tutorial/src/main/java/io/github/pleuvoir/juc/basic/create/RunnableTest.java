package io.github.pleuvoir.juc.basic.create;

public class RunnableTest implements Runnable {

	@Override
	public void run() {
		System.out.println("你好Runnable");
	}

	public static void main(String[] args) {

		new Thread(new RunnableTest()).start();
	}
}
