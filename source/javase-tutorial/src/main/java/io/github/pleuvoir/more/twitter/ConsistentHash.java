package io.github.pleuvoir.more.twitter;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;

/**
 * @author pleuvoir
 * 
 */
public class ConsistentHash {

	private final IHashFunction hashFunction; //哈希函数
	private final int numberOfReplicas; //所有的节点数
	private final SortedMap<Integer /** 节点哈希 **/, MachineNode /** 机器信息**/> circle = new TreeMap<Integer, MachineNode>(); //共有numberOfReplicas个元素

	public ConsistentHash(IHashFunction hashFunction, int numberOfReplicas, Collection<MachineNode> nodes) {
		this.hashFunction = hashFunction;
		this.numberOfReplicas = numberOfReplicas;
		//构建哈希环
		for (MachineNode node : nodes) {
			add(node);
		}
	}

	public void add(MachineNode node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			//树中的元素会按照自然排序
			circle.put(hashFunction.hash(node.toString() + i), node);
		}
	}
	
	
	public static void main(String[] args) {
		TreeMap map = new TreeMap<Integer, MachineNode>(); 
		
		MachineNode A = new MachineNode();
		MachineNode B = new MachineNode();
		MachineNode C = new MachineNode();
		MachineNode E = new MachineNode();
		MachineNode F = new MachineNode();
		
		map.put(5, E);
		map.put(6, F);
		map.put(1, A);
		map.put(2, B);
		map.put(3, C);
		
		System.out.println(JSON.toJSONString(map));
		
		SortedMap tailMap = map.tailMap(4);
		
	
		System.out.println(JSON.toJSONString(tailMap));
	}
	
	//沿环的顺时针找到虚拟节点
	public MachineNode get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}
		//计算数据的哈希值
		int hash = hashFunction.hash(key);
		if (!circle.containsKey(hash)) { //如果数据的哈希值没有落在节点上
			//找到该哈希值自然排序后的所有后续节点
			SortedMap<Integer, MachineNode> tailMap = circle.tailMap(hash);
			//有就取第一个，否则就取所有节点的第一个
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		// 如果数据的哈希值正好落在了节点上那直接取节点信息
		return circle.get(hash);
	}

	//机器信息
	private static class MachineNode {
		private String ip;
		@Override
		public String toString() {
			return this.ip;
		}
	}
	
	//哈希函数
	private static interface IHashFunction {
		int hash(Object key);
	}
}
