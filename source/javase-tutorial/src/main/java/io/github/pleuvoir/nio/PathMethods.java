package io.github.pleuvoir.nio;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path 使用
 *
 */
public class PathMethods {

	
	public static void main(String[] args) throws URISyntaxException {
		
		// Paths.get 是 FileSystems.getDefault().getPath的简写
		Path path = Paths.get("./xanadu.txt");
		System.out.println(path.toAbsolutePath());
		
		Path normalize = path.normalize();
		System.out.println(normalize.toAbsolutePath());
		
		Path path2 = FileSystems.getDefault().getPath("xanadu.txt");
		System.out.println(path2.toAbsolutePath());
		
		Path path3 = Paths.get(System.getProperty("user.dir"), "log","sub");
		System.out.println(path3);  // D:\03 space\01 git\middleware-roadmap\source\javase-tutorial\log\sub
		
		
		URI uri = normalize.toUri();  // 转换为一个浏览器可以打开的地址
		System.out.println(uri);
		
		// 读取classpath
		//D:\03 space\01 git\middleware-roadmap\source\javase-tutorial\target\classes\xanadu04.txt 新增的文件会在此目录下出现
		//Path currentDir = Paths.get(ClassLoader.getSystemResource("xanadu04.txt").toURI());
		Path currentDir = Paths.get(Thread.currentThread().getContextClassLoader().getResource("xanadu5.txt").toURI());
		System.out.println(currentDir.toAbsolutePath());
	}
}
