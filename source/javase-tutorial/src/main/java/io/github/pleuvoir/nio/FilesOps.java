package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.github.pleuvoir.io.CopyBytes;

/**
 * Files 操作
 * 
 * <p>
 * 注意，这!Files.exists(path)不等于Files.notExists(path)。当您测试文件存在时，可能会有三个结果：
 * 该文件已验证存在。
 *	该文件已验证不存在。
 *	文件的状态未知。当程序无权访问该文件时，可能会发生此结果。
 *	如果同时exists和notExists回报false，该文件的存在，无法验证。
 * <p>
 *
 */
public class FilesOps {

	public static void main(String[] args) throws IOException {
		checkExist();
		checkAccess();
		isSame();
		delete();
		deleteIfExists();
		copy();
		move();
	}

	// 检查是否存在
	private static void checkExist() {
		Path path = Paths.get(CopyBytes.filepath + "xanadu.txt");
		boolean exists = Files.exists(path);
		System.out.println(exists);
		boolean notExists = Files.notExists(path);
		System.out.println(notExists);
	}
	
	// 检查可访问性
	private static void checkAccess() {
		Path path = Paths.get(CopyBytes.filepath + "xanadu.txt");
		System.out.println(path + "可读？" + Files.isReadable(path));
		System.out.println(path + "可写？" + Files.isWritable(path));
	}
	
	// 是否相同
	private static void isSame() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "xanadu.txt");
		Path path2 = Paths.get(CopyBytes.filepath + "xanadu.txt");
		System.out.println("相同吗?" + Files.isSameFile(path, path2));
	}
	
	// 删除 文件不存在会抛出 java.nio.file.NoSuchFileException 因为是runtimeException 所以必须显式捕获判断
	private static void delete() {
		try {
			Files.delete(Paths.get(CopyBytes.filepath + "noexist.txt"));
		}catch(java.nio.file.NoSuchFileException e) {
			System.out.println(e.getMessage() +"没找到！删除失败");
		}catch(DirectoryNotEmptyException  e2) {  // 如果路径是目录那么目录不为空也不行
			System.out.println("目录不为空时不能删除!");
		}
		catch (IOException e) {
			//此处捕获了文件权限问题。
			e.printStackTrace();
		}
	}
	
	// 安静的删除，不会报错
	private static void deleteIfExists() throws IOException {
		System.out.println("安静的删除开始。");
		Files.deleteIfExists(Paths.get(CopyBytes.filepath + "noexist.txt"));
		System.out.println("安静的删除结束。");
	}
	
	
	// 复制文件或目录
	//具体可参考 https://docs.oracle.com/javase/tutorial/essential/io/copy.html
	private static void copy() {
		try {
			Path path = Paths.get(CopyBytes.filepath + "xanadu.txt");
			Path path2 = Paths.get(CopyBytes.filepath + "xanadu02.txt");
			Files.copy(path, path2);
			//Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("复制失败");
		}
	}
	
	// 移动文件或目录
	// https://docs.oracle.com/javase/tutorial/essential/io/move.html
	private static void move() {
		try {
			Path path = Paths.get(CopyBytes.filepath + "xanadu.txt");
			Path path3 = Paths.get(CopyBytes.filepath + "xanadu03.txt");
			Files.move(path, path3);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("移动失败");
		}
	}
	
}
