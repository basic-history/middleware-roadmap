package io.github.pleuvoir.juc.lock;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest2 {

	public static void main(String[] args) throws InterruptedException {
		Student student = new Student();
		Student student2 = new Student();
		Task first = new Task(student);
		Task second = new Task(student2);
		first.start();
		second.start();
		first.join();
		second.join();
	}

	public static class Student {
		private static synchronized void say() {
			//synchronized (this) {
				System.out.println("你好啊");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			//}
		}
	}

	public static class Task extends Thread {
		private Student student;
		public Task(Student student) {
			this.student = student;
		}
		@Override
		public void run() {
			student.say();
		}
	}
}
