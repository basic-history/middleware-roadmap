package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 文件更改通知 ，有收到多次通知的问题
 * 
 * <p>https://docs.oracle.com/javase/tutorial/essential/io/notification.html<p>
 *
 */
public class WatchServiceTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// 创建watchService
		WatchService watchService = FileSystems.getDefault().newWatchService();
		// 注册一个实现了Watchable接口的类，即可监听，这里我们使用Path
		// Path dir = Paths.get(CopyBytes.filepath + "watchservice.txt");
		Path dir = Paths.get(CopyBytes.filepath);
		// 注册变更事件
		WatchKey watchKey = dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

		for (;;) {
			// 阻塞获取
			WatchKey key;
			try {
				// System.out.println("阻塞获取事件。");
				key = watchService.take();
			} catch (InterruptedException e) {
				return;
			}

			System.out.println("获取到事件。");
			List<WatchEvent<?>> pollEvents = key.pollEvents();

			for (WatchEvent<?> event : pollEvents) {
				WatchEvent.Kind<?> kind = event.kind();
				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}
				Path filePath = dir.resolve((cast(event)).context());
				if (Files.isDirectory(filePath)) continue;
				System.out.println(filePath + "发生变更了。。");
			}
			// 必须重置否则无法继续监听，如果无效则退出循环
			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<Path> cast(WatchEvent<?> event) {
		return (WatchEvent<Path>) event;
	}
}
