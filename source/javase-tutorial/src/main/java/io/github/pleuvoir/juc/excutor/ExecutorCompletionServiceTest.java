package io.github.pleuvoir.juc.excutor;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * 方便从线程池中拿出已经完成的任务
 * @author pleuvoir
 *
 */
public class ExecutorCompletionServiceTest {

	// 负责生成文档
	private static ExecutorService docMakeService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	// 负责上传文档
	private static ExecutorService docUploadService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

	private static CompletionService<String> docCs = new ExecutorCompletionService<>(docMakeService);

	private static CompletionService<String> docUploadCs = new ExecutorCompletionService<>(docUploadService);

	public void go() throws InterruptedException, ExecutionException {
		

		
		//  1. 直接使用线程池，get返回的永远是按顺序的
//		for (int i = 0; i < 10; i++) {
//			int j = i;
//			Future<String> submit = docMakeService.submit(() -> makeDoc(j));
//			
//			System.out.println(submit.get());
//			
//			// 0 1 2 ..10 再提交的话肯定不是第一个返回的
//		//	docUploadService.submit(() -> uploadDoc( submit.get()));
//		}

		
		// 2 更优的实现
		// 打包成一个callable 提交到 被包装过后的 线程池，后面取的时候可以从队列中取出已经完成的任务
		
		for (int i = 0; i < 10; i++) {
			int j = i;
			docCs.submit(() -> makeDoc(j));
		}
		
//		// 继续往下一个线程池中投递
		for (int i = 0; i < 10; i++) {
			Future<String> take = docCs.take(); // 从队列中拿出已完成的那个，一定是最先完成的那个
			String id = take.get();
			docUploadCs.submit(() -> uploadDoc(id));
		}
		
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorCompletionServiceTest test = new ExecutorCompletionServiceTest();
		test.go();
	}

	private String makeDoc(int i) {
		//if(i%2==0){
			try {
				int nextInt = ThreadLocalRandom.current().nextInt(5);
			//	System.out.println(i + "休眠" + nextInt + "秒");
				TimeUnit.SECONDS.sleep(nextInt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	//	}
		return i + "_complete";
	}

	private String uploadDoc(String id) {
		System.out.println(id);
		return id + "up_complete";
	}

}
