package io.github.pleuvoir.more;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;

import io.github.pleuvoir.more.helper.BrokerData;

/**
 * 
 * 
 * 这是一个类似于  Map<String,List> 的数据结构
 * 
 *  往相同的key上放东西会 在对应的List中增加东西（无视重写后的equal即便相同也会重复添加），注意不要重复添加同一个对象多次 可能会有问题
 *
 */
public class ArrayListMultimapTest {

	static ArrayListMultimap<String, BrokerData> brokerTable = ArrayListMultimap.create();

	public static void main(String[] args) {

		BrokerData data1 = new BrokerData();
		data1.setBrokerInstanceName("1");

		BrokerData data2 = new BrokerData();
		data2.setBrokerInstanceName("2");

		BrokerData data3 = new BrokerData();
		data3.setBrokerInstanceName("1");

		brokerTable.put("1", data1);
		brokerTable.put("2", data2);

		brokerTable.put("1", data3); // 会在1对应的list里添加东西

		System.out.println(JSON.toJSONString(brokerTable, true));
	}

}
