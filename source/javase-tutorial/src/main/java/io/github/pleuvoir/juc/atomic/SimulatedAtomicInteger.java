
package io.github.pleuvoir.juc.atomic;

/**
 * 模拟AtomicInteger
 */
public class SimulatedAtomicInteger extends SimulatedCAS {

	public SimulatedAtomicInteger(int initialValue) {
		super(initialValue);
	}

	public int incrementAndGet() {
		for (;;) {
			int current = value;
			int next = current + 1;
			System.out.println("current=" + current + ", next=" + next);
			if (compareAndSet(current, next)) {
				return next;
			}
		}
	}

}
