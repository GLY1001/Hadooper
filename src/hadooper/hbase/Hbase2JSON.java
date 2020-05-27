package hadooper.hbase;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Hbase2JSON {
	private byte[] family = "f".getBytes();
	private ResultScanner scanner;
	
	public Hbase2JSON() {
	}
	public Hbase2JSON(String family) {
		this.family = family.getBytes();
	}
	/* 获取json数组对象(使用过滤器)
	 * table 传入表
	 * col1 查找的列名(name)
	 * col2 查找的列名 (value)
	 * param 过滤器过滤的列名
	 * value 过滤值
	 * */
	public JSONArray getFilter2A(Table table ,String col1 ,String col2 , String param ,String value) throws IOException {
		JSONArray a = new JSONArray();
		byte[] qualifier = param.getBytes();
		TreeMap<String, Double> map = new TreeMap<String, Double>();
		scanner = HbaseOpUtil.getRS(table, family, qualifier, value);
		for (Result r : scanner) {
			map.put(new String(r.getValue(family, col1.getBytes())), Double.parseDouble(new String(r.getValue(family, col2.getBytes()))));			
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                // 降序排序
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        for (Entry<?, ?> e : list) {
        	if(e.getKey().equals("")) continue;
        	JSONObject o = new JSONObject();
        	o.put("name", e.getKey());
        	o.put("value", e.getValue());
        	a.add(o);
        }
        table.close();
		return a;
	}
	/* 获取json类对象(使用过滤器)
	 * table 传入表
	 * col1 查找的列名 (x)
	 * col2 查找的列名 (y)
	 * param 过滤器过滤的列名
	 * value 过滤值
	 * */
	public JSONObject getFilter2O(Table table ,String col1 ,String col2, String param, String value) throws IOException {
		JSONObject o = new JSONObject();
		JSONArray a1 = new JSONArray();
		JSONArray a2 = new JSONArray();
		byte[] qualifier = param.getBytes();
		TreeMap<String, Double> map = new TreeMap<String, Double>();
		scanner = HbaseOpUtil.getRS(table, family, qualifier, value);
		for (Result r : scanner) {
			map.put(new String(r.getValue(family, col1.getBytes())), 
					Double.parseDouble(new String(r.getValue(family, col2.getBytes()))));			
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                // 降序排序
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        for (Entry<?, ?> e : list) {
        	a1.add(e.getKey());
        	a2.add(e.getValue());
        }
        o.put(col1, a1);
        o.put(col2, a2);
        table.close();
		return o;
	}
	/* 获取json类对象(使用 多 过滤器)
	 * table 传入表
	 * col1 查找的列名 (x)
	 * col2 查找的列名 (y)
	 * param 过滤器过滤的列名
	 * param1 过滤器过滤的列名
	 * value 过滤值
	 * value1 过滤值
	 * */
	public JSONObject getFilter2O_AND(Table table ,String col1 ,String col2, String param,String param1, String value, String value1) throws IOException {
		JSONObject o = new JSONObject();
		JSONArray a1 = new JSONArray();
		JSONArray a2 = new JSONArray();
		byte[] q1 = param.getBytes();
		byte[] q2 = param1.getBytes();
		TreeMap<String, Double> map = new TreeMap<String, Double>();
		scanner = HbaseOpUtil.getRS_AND(table, family, q1, q2, value, value1);
		for (Result r : scanner) {
			map.put(new String(r.getValue(family, col1.getBytes())), 
					Double.parseDouble(new String(r.getValue(family, col2.getBytes()))));			
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                // 降序排序
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        for (Entry<?, ?> e : list) {
        	a1.add(e.getKey());
        	a2.add(e.getValue());
        }
        o.put(col1, a1);
        o.put(col2, a2);
        table.close();
		return o;
	}

	/* 获取json类对象(使用 多 过滤器)
	 * table 传入表
	 * col1 查找的列名 (x)
	 * col2 查找的列名 (y)
	 * param 过滤器过滤的列名
	 * param1 过滤器过滤的列名
	 * value 过滤值
	 * value1 过滤值
	 * */
	public JSONArray getFilter2A_AND(Table table ,String col1 ,String col2, String param,String param1, String value, String value1) throws IOException {
		JSONArray a = new JSONArray();
		byte[] q1 = param.getBytes();
		byte[] q2 = param1.getBytes();
		TreeMap<String, Double> map = new TreeMap<String, Double>();
		scanner = HbaseOpUtil.getRS_AND(table, family, q1, q2, value, value1);
		for (Result r : scanner) {
			map.put(new String(r.getValue(family, col1.getBytes())), Double.parseDouble(new String(r.getValue(family, col2.getBytes()))));			
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
                // 降序排序
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        for (Entry<?, ?> e : list) {
        	if(e.getKey().equals("")) continue;
        	JSONObject o = new JSONObject();
        	o.put("name", e.getKey());
        	o.put("value", e.getValue());
        	a.add(o);
        }
        table.close();
		return a;
	}
	
}
