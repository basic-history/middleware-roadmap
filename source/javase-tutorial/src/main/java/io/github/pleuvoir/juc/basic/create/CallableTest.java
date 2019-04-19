package io.github.pleuvoir.juc.basic.create;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableTest implements Callable<String> {

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(3);
		return "你好Callable";
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		FutureTask<String> futureTask = new FutureTask<>(new CallableTest());
		
		new Thread(futureTask).start();
		
		System.out.println("已启动");
		String result = futureTask.get(); //阻塞获取
		System.out.println("返回结果");
		System.out.println(result);
	}
}
