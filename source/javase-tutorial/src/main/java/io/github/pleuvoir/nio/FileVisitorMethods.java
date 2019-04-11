package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 递归遍历文件树
 * <p>
 *     您是否需要创建一个递归访问文件树中所有文件的应用程序？也许您需要删除.class树中的每个文件，或者查找去年未访问过的每个文件。您可以使用FileVisitor界面执行此 操作。
 * </p>
 *
 * https://docs.oracle.com/javase/tutorial/essential/io/walk.html
 *
 */
public class FileVisitorMethods {

    public static void main(String[] args) throws IOException {
        find();
    }

    private static void find() throws IOException {
        String p = "D:\\03 space\\01 git\\middleware-roadmap\\source\\javase-tutorial\\src\\main\\java\\io\\github\\pleuvoir";
        Path path = Paths.get(p);
		Files.walkFileTree(path, new ApacheLicenseFileVisitor());
    }

    static class ApacheLicenseFileVisitor extends SimpleFileVisitor<Path>{


//        @Override
//        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//            System.out.println("正在访问目录："+ dir.toAbsolutePath());
//            return super.postVisitDirectory(dir, exc);
//        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
           // System.out.println("当前访问的文件：" + file.getFileName());
            if(file.getFileName().toString().endsWith(".java")){
                System.out.println("找到java文件：" + file);
                return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            System.out.println("访问文件失败" + exc);
            return super.visitFileFailed(file, exc);
        }
    }



}
