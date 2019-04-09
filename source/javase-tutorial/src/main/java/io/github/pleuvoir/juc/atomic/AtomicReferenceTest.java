package io.github.pleuvoir.juc.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

	static AtomicReference<User> ar = new AtomicReference<>();

	public static void main(String[] args) {
		User oldUser = new User("pleuvoir", 18); //要修改的对象实例
		ar.set(oldUser);
		
		System.out.println("oldUser=" + oldUser + ", ar=" + ar.get());
		
		oldUser.setAge(14); //这一步修改了对象属性，会发现原子引用中get的也变了
		
		System.out.println("oldUser=" + oldUser + ", ar=" + ar.get());
		
		User newUser = new User("duke", 27); 
		boolean flag = ar.compareAndSet(oldUser, newUser); //交换成功后只有newUser的修改才会改变原子引用
		
		System.out.println("更新成功？" + flag + ", oldUser=" + oldUser + ", ar=" + ar.get());
		
		oldUser.setName("pleuvoir~");
		
		System.out.println("oldUser=" + oldUser + ", newUser=" + newUser + ", ar=" + ar.get());
		
		newUser.setName("duke~");
		System.out.println("oldUser=" + oldUser + ", newUser=" + newUser + ", ar=" + ar.get());
	}

	public static class User {

		private String name;
		private int age;

		public User(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return String.format("User [name=%s, age=%s]", name, age);
		}

	}
}
