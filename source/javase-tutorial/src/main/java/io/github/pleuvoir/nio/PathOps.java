package io.github.pleuvoir.nio;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Path 使用
 *
 */
public class PathOps {

	
	public static void main(String[] args) {
		
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
	}
}
