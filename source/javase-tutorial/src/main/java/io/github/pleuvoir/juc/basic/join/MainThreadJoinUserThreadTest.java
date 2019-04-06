package io.github.pleuvoir.juc.basic.join;

/**
 * 主线程等待用户线程
 * 
 * t1，t2，t3将在main之前执行，但它们的执行顺序无法保证
 *
 */
public class MainThreadJoinUserThreadTest {

	public static void main(String[] args) throws InterruptedException {

		T t1 = new T("t1");
		T t2 = new T("t2");
		T t3 = new T("t3");

		t1.start();
		t2.start();
		t3.start();
		
		t1.join(); //当前线程即主线程将在t1后执行
		t2.join();//当前线程即主线程将在t2后执行
		t3.join();//当前线程即主线程将在t3后执行

		System.out.println("I am main");
	}

	public static class T extends Thread {

		public T(String name) {
			super(name);
		}

		@Override
		public void run() {
			System.out.println(getName() + " " + System.currentTimeMillis());
		}
	}
}
