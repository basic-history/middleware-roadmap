package io.github.pleuvoir.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象流
 * <p>
 * 读写过后对象内存会被重新构建
 * <p>
 */
public class ObjectStream {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

		// 写文件
		A a = new A();
		B b = new B();
		b.setName("duke");
		a.setB(b);
		
		System.out.println("memory address:" + a);
		System.out.println("memory address:" + b);
	
		write("ObjectStreamTest", a);

		A readedA = read("ObjectStreamTest", A.class);
		A readedB = read("ObjectStreamTest", A.class);
		
		System.out.println("memory address:" + readedA);
		System.out.println("memory address:" + readedA.getB());
		
		System.out.println(readedA.getB().getName());
		
		System.out.println("不同流读多次是否一样？" + readedA.equals(readedB)); //false 不一样
		
		// 同一个流读多次应是一样的，可能会出现EOFException
	}

	
	private static  void write(String path, Object o) throws IOException {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(CopyBytes.filepath + path)));
			oos.writeObject(o);
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")  // 当不能转换为正确的对象类型时抛出ClassNotFoundException
	private static <T> T read(String path,Class<T> clazz) throws ClassNotFoundException, IOException {
		// 读文件
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(CopyBytes.filepath + path)));
			return (T) ois.readObject();
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	static class A implements Serializable {
		private static final long serialVersionUID = 1L;
		private B b;
		public B getB() {
			return b;
		}
		public void setB(B b) {
			this.b = b;
		}
	}

	static class B implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
