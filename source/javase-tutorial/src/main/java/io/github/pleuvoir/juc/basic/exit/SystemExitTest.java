package io.github.pleuvoir.juc.basic.exit;

public class SystemExitTest {

	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(11);
			}
		}));

		try {
			System.out.println("try");
			
			//System.exit(-1);
			
			// 如果使用了这句则会直接退出 不会触发钩子函数 finally也不会执行
			Runtime.getRuntime().halt(1);
		} finally {
			System.out.println("finally");
		}

	}
}
