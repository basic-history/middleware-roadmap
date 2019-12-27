package io.github.pleuvoir.more.twitter;

public class IdUtils {

	static IdWorker woker = new IdWorker();

	public static long nextId() {
		return woker.nextId();
	}

}
