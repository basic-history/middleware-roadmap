package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 目录操作
 *
 */
public class DirectoriesMethods {

	public static void main(String[] args) throws IOException {
		
		
		//创建临时目录
		Files.createTempDirectory("temp");
		
		// 创建多层级目录
		Files.createDirectories(Paths.get(CopyBytes.filepath,"test","test2"));
		
		//打印根目录
		FileSystems.getDefault().getRootDirectories().forEach(p->{
			System.out.println(p);
		});
		
		// 列出指定目录下的 java,class,jar 文件，不会递归
		try(DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(CopyBytes.filepath,"temp"),
				"*.{java,class,jar}");){
			newDirectoryStream.forEach(n->{
				System.out.println(n);
			});
		}
		
	}
	
}
