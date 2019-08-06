package io.github.pleuvoir.juc.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CompletabledFuture 增强的future，这个类只有1.8有
 * 
 * @author pleuvoir
 *
 */
public class CompletabledFutureTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

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
	}

}
