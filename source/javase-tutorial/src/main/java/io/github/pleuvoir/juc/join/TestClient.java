package io.github.pleuvoir.juc.join;

public class TestClient {

	public static void main(String[] args) throws InterruptedException {

		// 主线程 seq1Task seq2Task seq3Task
		Thread main = Thread.currentThread();

		SeqTask seq1Task = new SeqTask(main, "seq1Task"); // 将主线程插到seq1Task之前

		SeqTask seq2Task = new SeqTask(seq1Task, "seq2Task"); // 将seq1Task插到seq2Task之前

		SeqTask seq3Task = new SeqTask(seq2Task, "seq3Task"); // 将seq2Task插到seq3Task之前

		seq3Task.start();
		seq1Task.start();
		seq2Task.start();
		Thread.sleep(2000);
	}
}
