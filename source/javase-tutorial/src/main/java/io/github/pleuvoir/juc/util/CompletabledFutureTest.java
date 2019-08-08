package io.github.pleuvoir.juc.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CompletabledFuture 增强的future，这个类只有1.8有
 * 
 * @author pleuvoir
 *
 */
public class CompletabledFutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		// 执行有返回值的future，不指定默认在ForkJoinPool.commonPool
		CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " return 5");
			return 5;
		});

		//阻塞2秒后才会得到结果
		System.out.println(supplyAsync.get());
		
		
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		
		CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName() + " return 6");
			return 6;
		}, newCachedThreadPool);

		// 执行没有返回值的任务
		CompletableFuture.runAsync(() -> {
			System.out.println(Thread.currentThread().getName() + " sout 1");
		});
		
	
		newCachedThreadPool.shutdown(); //非deamon 需要手动关闭
		
		
		//流逝调用
		CompletableFuture.supplyAsync(() -> 5)
				.thenApply(i -> i * 6)
				.thenApply(j -> j * 9)
				.thenAccept(System.out::println);
		
		
		//异常的情况
		CompletableFuture.supplyAsync(() -> 5 / 0).exceptionally(e -> {
			e.printStackTrace();
			return 0;
		});
		
		
		//完成后通知我
		
		CompletableFuture<Object> promise = new CompletableFuture<Object>();
		
		CompletableFuture.runAsync(() ->{
			try {
				System.out.println(promise.get());
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		
		TimeUnit.SECONDS.sleep(3);
		// 告知完成，
		promise.complete("完成了");
		
		//等待一会，如果超时或者执行出现异常都会抛出异常，这个异常需要手动捕获
		CompletableFuture<Object> promise2 = new CompletableFuture<Object>();
		CompletableFuture.runAsync(() ->{
			try {
				
				int nextInt = ThreadLocalRandom.current().nextInt(1,5);
				TimeUnit.SECONDS.sleep(nextInt);
			//	promise2.complete(nextInt);
				
			//	promise2.completeExceptionally(new RuntimeException("自定义异常"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
		Object result = null;
		try {
			result = promise2.get(1, TimeUnit.SECONDS);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("1秒过去了，结果result="+result);
		
	}

}
