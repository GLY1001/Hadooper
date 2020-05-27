package hadooper.hbase;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.*;

import net.sf.json.*;

public class HbaseIndex_scan {
	private Table table;
	private ResultScanner scanner;
	private byte[] family ;
	private byte[] qualifier ;
	//初始化获取表的实例，传入列族名，列限定符，主要由使用过滤器的rs
	public HbaseIndex_scan(Table table,String family, String qualifier) throws IOException{
		this.table = table;
		this.qualifier = qualifier.getBytes();
		this.family = family.getBytes();
	}
	public HbaseIndex_scan(Table table,String family) throws IOException{
		this.table = table;
		this.family = family.getBytes();
	}
	//初始化获取hbase表实例，传入列族名
	public HbaseIndex_scan(Table table) throws IOException{
		this.table = table;
		family = "f".getBytes();
	}
	/*
	 * 获取要使用过滤器的json数组对象
	 * @param param 过滤器的关键值
	 * @param colname1|2 是数据需要获取的列限定符名
	 * @param y 是获取排名前y个
	 * */
	public JSONArray getFilterArr(String param, String colname1, String colname2 ,int y) throws IOException {
		JSONArray a = new JSONArray();
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		scanner = HbaseOpUtil.getRS(table, family, qualifier, param);
		for (Result r : scanner) {
			map.put(new String(r.getValue(family, colname1.getBytes())), Integer.parseInt(new String(r.getValue(family, colname2.getBytes()))));			
		}
		List <Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                // 降序排序
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        int count = 0;
        for (Entry<?, ?> e : list) {
        	if(count == y)	break;
        	if(e.getKey().equals("")) continue;
        	count += 1;
        	JSONObject o = new JSONObject();
        	o.put("name", e.getKey());
        	o.put("value", e.getValue());
        	a.add(o);
        }
        table.close();
		return a;
	}
	/*	
	 * 获取json对象数组
	 * @param colname1|2 是需要的列限定符名
	 * @param x 是判断topn数量以及数据类型 是integer还是double
	 * */
	public JSONObject getObj(String colname1, String colname2 ,Object x) throws IOException {
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		JSONObject ob = new JSONObject();
		JSONArray a1 =  new JSONArray();
		JSONArray a2 = new JSONArray();
		Scan scan = new Scan();
		scanner = table.getScanner(scan);
		int y;
		//判断数据类型
		if (x.getClass() == Integer.class) {
			y = (int)x;
		for (Result r : scanner) {
			map.put(	new String(r.getValue(family, colname1.getBytes())), 
						Integer.parseInt(new String(r.getValue(family, colname2.getBytes()))));
			
		}
		List <Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
		 // 通过比较器来实现排序 TreeMap按照value排序
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
                // 降序排序
                return ((Integer) o2.getValue()).compareTo((Integer) o1.getValue());
            }
        });
        int count = 0;
        for (Entry<String, Object> e : list) {
			if(count == y)	break;
        	if(e.getKey().equals("")) continue;
        	count += 1;
        	a1.add(e.getKey());
        	a2.add(e.getValue());
        }
		}else {
			y = new Double((double) x).intValue();
			for (Result r : scanner) {
				map.put(	new String(r.getValue(family, colname1.getBytes())), 
							Double.parseDouble(new String(r.getValue(family, colname2.getBytes()))));
				
			}
			List <Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			 // 通过比较器来实现排序 TreeMap按照value排序
	        Collections.sort(list, new Comparator<Map.Entry<String, Object>>() {
	            @Override
	            public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
	                // 降序排序
	                return ((Double) o2.getValue()).compareTo((Double) o1.getValue());
	            }
	        });
        int count = 0;
        for (Entry<String, Object> e : list) {
			if(count == y)	break;
        	if(e.getKey().equals("")) continue;
			String name = e.getKey().trim();
			if (name.contains("(职位编号：001)")) {
				name = name.split("(职位编号：001)")[0].trim();
			}
        	count += 1;
        	a1.add(name);
        	a2.add(e.getValue());
        }
		}
        ob.put(colname1, a1);
        ob.put(colname2, a2);
        table.close();
		return ob;
	}
	/*	
	 * 获取json数组对象
	 * @param colname1|2 是需要的列限定符名
	 * @param x 是判断topn的数量以及数据类型 是integer还是double
	 * */
	public JSONArray getArr(String colname1, String colname2 ,Object x) throws IOException {
		JSONArray all = new JSONArray();
		Scan scan = new Scan();
		scanner = table.getScanner(scan);
		if (x.getClass() == Integer.class) {
			for (Result r : scanner) {
				JSONObject obj = new JSONObject();
				obj.put("name",new String(r.getValue(family, colname1.getBytes())));
				obj.put("value",Integer.parseInt((new String(r.getValue(family, colname2.getBytes())))));	
				all.add(obj);
			}
			}else {
			for (Result r : scanner) {
			JSONObject obj = new JSONObject();
			obj.put("name",new String(r.getValue(family, colname1.getBytes())));
			obj.put("value",Double.parseDouble((new String(r.getValue(family, colname2.getBytes())))));
			all.add(obj);
			}
			}
        table.close();
		return all;
	}

	/*	
	 * 获取雷达图的json数组对象
	 * */
	public JSONArray getRadar() throws IOException{
		JSONArray objArray = new JSONArray();
		//遍历数组，传参数到hbase里取指定学历的值
		String[] sw = new String[]{"高中","博士","博士后","高职","统招本科","硕士及以上","本科及以上","学历不限","大专及以上","初中及以上","中职","中专/中技及以上","MBA及以上"};
		for(String s:sw) {
			JSONObject object = new JSONObject();
			int[] arr = new int[5];
			ResultScanner result = HbaseOpUtil.getRS(table, "f".getBytes(), "edu_level".getBytes(), s);
			//处理数据，让数据变得规整
			for(Result r:result) {
				switch(new String(r.getValue("f".getBytes(), "people".getBytes()))) {
					case "0-100人":
						arr[2] = Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())));
						break;
					case "500-1000人":
						arr[1] = Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())));
						break;
					case "1000-5000人":
						arr[4] = Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())));
						break;
					case "5000-10000人":
						arr[0] = Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())));
						break;
					case "10000以上":
						if(Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())))==14)
							continue;
						arr[3] = Integer.parseInt(new String(r.getValue("f".getBytes(), "amount".getBytes())));
						break;
				}
				object.put("name", s);
				object.put("value", arr);
			}
			objArray.add(object);
		}
		return objArray;
	}
	/*
	 * 获取处理过的industry字段内容的list
	 *  @param name1|2 为hbase表的列限定符名分别为name value
	 * */
	public JSONObject getIndustry(String name1, String name2 ) throws IOException {
		ResultScanner rs = table.getScanner(new Scan());
		List<Map.Entry<String, Integer>> list = HbaseOpUtil.industrySplit(rs, family, name1, name2);
		JSONObject object = new JSONObject();
		JSONArray a1 = new JSONArray();
		JSONArray a2 = new JSONArray();
		for (Entry<String, Integer> e : list) {
			if(e.getValue()>5000) {
			a1.add(e.getKey());
			a2.add(e.getValue());
		}
		}
		object.put(name1, a1);
		object.put(name2, a2);
		return object;
	}
}
