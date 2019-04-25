package io.github.pleuvoir.juc.excutor.guava;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 带回调的future
 * @author pleuvoir
 *
 */
public class MoreFutureTest {

	public static void main(String[] args) throws InterruptedException {
		List<User> list = Lists.newArrayList();
		// 先包装一下
		ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
		// 提交任务

		for (int i = 0; i < 5; i++) {
			ListenableFuture<User> future = service.submit(new CallableTask());
			Futures.addCallback(future, new FutureCallback<User>() {
				@Override
				public void onSuccess(User result) {
					System.out.println(Thread.currentThread().getName() + "@" + result.getName());
					list.add(result);
				}

				@Override
				public void onFailure(Throwable t) {
					System.err.println(t.getMessage());
				}
			}, MoreExecutors.directExecutor());
		}
		
		// 阻塞优雅停机，超时则中断任务，中断时间建议设置为 线程执行时间的 2倍？ 因为它会先等待除2的时间
		MoreExecutors.shutdownAndAwaitTermination(service, 8, TimeUnit.SECONDS);
		
		list.forEach(u->{
			System.out.println(u.getName());
		});
	}

	static class User {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static class CallableTask implements Callable<User> {
		@Override
		public User call() throws Exception {
			if (ThreadLocalRandom.current().nextBoolean()) {
				throw new RuntimeException("自定义异常");
			}
			TimeUnit.SECONDS.sleep(3);
			User user = new User();
			user.setName("pleuvoir");
			return user;
		}
	}
}
