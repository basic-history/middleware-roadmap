package io.github.pleuvoir.more.guava;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Supplier;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

/**
 * 类似EXCEL表格一样
 * @author pleuvoir
 *
 */
public class TableTest {

	private static Table<String, String, Object> hsmServices = Tables
			.newCustomTable(new ConcurrentHashMap<String, Map<String,Object>>(), new Supplier<Map<String,Object>>(){
		@Override
		public Map<String, Object> get() {
			return new ConcurrentHashMap<String, Object>();
		}
	});
	
	
	
	/**
	 * 		
	 * 
	 *   ------------------------
	 *   |	行1-列1	|	行1-列2	|
	 *   |----------------------
	 *   |	行2-列1	|	行2-列2	|
	 *   -----------------------|
	 * 
	 * 
	 */
	
	public static void main(String[] args) {
		
		hsmServices.put("行1", "列1", "行1-列1");
		hsmServices.put("行1", "列2", "行1-列2");
		hsmServices.put("行2", "列1", "行2-列1");
		hsmServices.put("行2", "列2", "行2-列2");
		
		Object object = hsmServices.get("行1", "列1");
		System.out.println(object);
	}
}
