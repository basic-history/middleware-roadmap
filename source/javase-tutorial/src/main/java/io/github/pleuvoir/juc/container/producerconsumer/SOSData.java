package io.github.pleuvoir.juc.container.producerconsumer;

public class SOSData {

	private final long sosTime;

	public SOSData(long sosTime) {
		this.sosTime = sosTime;
	}

	public long getSosTime() {
		return sosTime;
	}

	@Override
	public String toString() {
		return String.format("SOSData [sosTime=%s]", sosTime);
	}

}
