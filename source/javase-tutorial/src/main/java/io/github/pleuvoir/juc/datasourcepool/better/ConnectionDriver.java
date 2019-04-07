package io.github.pleuvoir.juc.datasourcepool.better;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class ConnectionDriver {

	static class ConnectionHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("createStatement")) {
				Thread.sleep(100);
			}
			return null;//不返回结果
		}
	}

	public static Connection fetchOneConnection() {
		return (Connection) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class<?>[] { Connection.class }, new ConnectionHandler());
	}

}
