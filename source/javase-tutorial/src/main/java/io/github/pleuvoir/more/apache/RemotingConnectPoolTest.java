package io.github.pleuvoir.more.apache;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.alibaba.fastjson.JSON;

public class RemotingConnectPoolTest {

	public static void main(String[] args) {
		/** 连接池的配置 */
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

		/** 下面的配置均为默认配置,默认配置的参数可以在BaseObjectPoolConfig中找到 */
		poolConfig.setMaxTotal(10); // 池中的最大连接数
		poolConfig.setMinIdle(3); // 最少的空闲连接数
		poolConfig.setMaxIdle(8); // 最多的空闲连接数
		poolConfig.setMaxWaitMillis(-1); // 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常 单位:毫秒数
		poolConfig.setLifo(true); // 连接池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
		poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 30L); // 连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
		poolConfig.setBlockWhenExhausted(false); // 连接耗尽时是否阻塞,默认为true

		
		System.out.println(JSON.toJSONString(poolConfig,true));
		/** 连接池创建 */
		
		GenericObjectPool<RemotingConnect> objectPool = new GenericObjectPool<>(new RemotingConnectPool(), poolConfig);

		RemotingConnect obj = null;
		try {
			obj = objectPool.borrowObject();
			System.out.println(objectPool.borrowObject());
			System.out.println(objectPool.borrowObject());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objectPool.returnObject(obj);
		}
	}
}
