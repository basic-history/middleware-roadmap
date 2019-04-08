
package io.github.pleuvoir.juc.atomic;

/**
 * 模拟CAS操作
 * 
 */
public class SimulatedCAS {

	protected int value;

	public SimulatedCAS(int initialValue) { // Unsafe中通过offset定位字段，我们这里为方便起见直接使用初始化值
		this.value = initialValue;
	}

	public synchronized int get() {
		return value;
	}

	// 当期望值=旧值时成功
	public synchronized boolean compareAndSet(int expectedValue, int newValue) {
		return (expectedValue == compareAndSwap(expectedValue, newValue));
	}

	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		int oldValue = value;
		if (oldValue == expectedValue) {
			value = newValue;
		}
		System.out.println("oldValue=" + oldValue + ", expectedValue=" + expectedValue + ", newValue=" + newValue);
		return oldValue;
	}
}
