package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 常用的方法，此类方法用于处理小文件，并且会自动清理流
 *  <p>
 *  
 *  StandardOpenOptions支持以下枚举：
		WRITE - 打开文件以进行写访问。
		APPEND - 将新数据附加到文件末尾。此选项与WRITE或CREATE选项一起使用。
		TRUNCATE_EXISTING - 将文件截断为零字节。此选项与WRITE选项一起使用。
		CREATE_NEW - 如果文件已存在，则创建新文件并引发异常。
		CREATE - 打开文件（如果存在）或创建新文件（如果不存在）。
		DELETE_ON_CLOSE - 关闭流时删除文件。此选项对临时文件很有用。
		SPARSE - 提示新创建的文件将是稀疏的。此高级选项在某些文件系统（例如NTFS）上受到尊重，其中具有数据“间隙”的大文件可以以更有效的方式存储，其中这些空间隙不占用磁盘空间。
		SYNC - 使文件（内容和元数据）与底层存储设备保持同步。
		DSYNC - 使文件内容与底层存储设备保持同步。
 *  <p>
 */
public class ReadWriteSmallFilesMethod {
	
	
	public static void main(String[] args) throws IOException {
		readSmallFileAllLines();
		readSmallFileAllBytes();
		writeBytes();
	}

	// 如果您有一个小文件，并且您希望一次性读取其全部内容，则可以使用 readAllBytes(Path)或 readAllLines(Path,
	// Charset)方法。这些方法可以为您完成大部分工作，例如打开和关闭流，但不用于处理大型文件。
	private static void readSmallFileAllLines() throws IOException {
		List<String> readAllLines = Files.readAllLines(Paths.get(CopyBytes.filepath + "small-file"));
		for (String string : readAllLines) {
			System.out.println(string);
		}
	}
	
	// 读取字节转为String 即为所有的内容
	private static void readSmallFileAllBytes() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(CopyBytes.filepath + "small-file"));
		String string = new String(bytes,StandardCharsets.UTF_8);
		System.out.println("readSmallFileAllBytes to string :" + string);
	}
	
	private static void writeBytes() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "writeBytes-file");
		Files.write(path, 
				new StringBuilder().append("1").append("\n").append("2").toString().getBytes(),
				StandardOpenOption.CREATE); // 或者不要此参数
	}
	
}
