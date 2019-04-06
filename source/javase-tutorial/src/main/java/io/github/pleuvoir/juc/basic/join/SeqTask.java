package io.github.pleuvoir.juc.basic.join;

public class SeqTask extends Thread {

	private Thread thread;
	private String name;

	public SeqTask(Thread before, String name) {
		this.thread = before;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			thread.join();  //thread线程将在本线程执行之前执行
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(name + " runing..");
	}
}
