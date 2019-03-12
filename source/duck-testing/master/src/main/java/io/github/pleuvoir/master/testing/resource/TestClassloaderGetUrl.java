package io.github.pleuvoir.master.testing.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import io.github.pleuvoir.slave.testing.spi.ClassHelper;


public class TestClassloaderGetUrl {

	public static void main(String[] args) {
		TestClassloaderGetUrl testClassloaderGetUrl = new TestClassloaderGetUrl();
		testClassloaderGetUrl.createDBTable();
	}
	
	private void createDBTable() {
		String fileName = "oracle_create_table.sql";
		try {
			 Enumeration<java.net.URL> urls;
			   ClassLoader classLoader =  ClassHelper.getClassLoader(getClass());
	            if (classLoader != null) {
	                urls = classLoader.getResources(fileName);
	            } else {
	                urls = ClassLoader.getSystemResources(fileName);
	            }
			if (urls != null) {
				while (urls.hasMoreElements()) {
					java.net.URL url = urls.nextElement();
					
					String sql = file2String(url);
					System.out.println("发现文件。。" + sql);
				}
			}
		
		} catch (Exception e) {
			throw new IllegalStateException("创建可靠消息记录表失败", e);
		}
	}

	public static String file2String(final URL url) {
		InputStream in = null;
		try {
			URLConnection urlConnection = url.openConnection();
			urlConnection.setUseCaches(false);
			in = urlConnection.getInputStream();
			int len = in.available();
			byte[] data = new byte[len];
			in.read(data, 0, len);
			return new String(data, "UTF-8");
		} catch (Exception ignored) {
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
		return null;
	}

}
