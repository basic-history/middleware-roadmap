package test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MagicNumberTest {

	 private static final int HASH_INCREMENT = 0x61c88647;
	 
	 private static AtomicInteger nextHashCode =
		        new AtomicInteger();
	 
	public static void main(String[] args) throws InterruptedException {
		
		for(int i=0;i<100;i++) {
			TimeUnit.SECONDS.sleep(1);
			System.out.println(nextHashCode()%2);
		}
	}
	
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
}
