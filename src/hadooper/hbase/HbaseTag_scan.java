package hadooper.hbase;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;

import net.sf.json.*;

public class HbaseTag_scan {
	private Table table;
	private  byte[] family = "f".getBytes();
	private  byte[] qualifier = "tag".getBytes();
	private String param;
	/*初始化，获取hbase表实例
	 * @param tablename hbase表名
	 * @param param 过滤的关键值
	 * */
	public HbaseTag_scan(String tablename, String param) throws IOException {
		table = HbaseOpUtil.getTable(tablename);
		this.param = param;
	}
	
	//返回json对象
	public JSONObject getObj(String tag) throws IOException {
		JSONArray count = new JSONArray();
		JSONArray a1 = new JSONArray();
		JSONObject object = new JSONObject();
		Map<String, Integer> tree = new TreeMap<String, Integer>();
		ResultScanner scanner = HbaseOpUtil.getRS(table, family, qualifier , tag);
		for (Result r : scanner) {
			int x  = Integer.parseInt(new String(r.getValue("f".getBytes(), "count".getBytes())));
			String y = new String(r.getValue("f".getBytes(), param.getBytes())) ;
				tree.put(y, x);
		}
		List <Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(tree.entrySet());
		
		 // 通过比较器来实现排序 TreeMap按照value排序
       Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
           @Override
           public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
               // 降序排序
               return o2.getValue().compareTo(o1.getValue());
           }
       });
       
       for (Entry<String, Integer> e : list) {
       	a1.add(e.getKey());
       	count.add(e.getValue());
       }
		object.put("count", count);
		object.put(this.param, a1);
		return object;
	}
	//返回数组，饼图等可以用这个方法
	public JSONArray getArr(String tag) throws IOException{
		JSONArray all = new JSONArray();
		ResultScanner scanner = HbaseOpUtil.getRS(table, family, qualifier , tag);
		for (Result r : scanner) {
			String x = new String(r.getValue("f".getBytes(), "count".getBytes())) ;
			String y = new String(r.getValue("f".getBytes(), param.getBytes())) ;
	       	JSONObject ob = new JSONObject();
	       	ob.put("value", x);
	       	ob.put("name", y);
	       	all.add(ob);	
       }
		return all;
	}
	/*
	 * 获取处理过的industry字段内容的list
	 *  @param tag 为过滤的关键字
	 * */ 
	public JSONArray getIndustry( String tag) throws IOException {
		ResultScanner rs = HbaseOpUtil.getRS(table, family, qualifier , tag);
		List<Map.Entry<String, Integer>> list = HbaseOpUtil.industrySplit(rs, family, param, "count");
		JSONArray a = new JSONArray();int len = list.size();
		int count = 0;
		if (len >= 100 ) {
			len = 100;
		}
		for (Entry<String, Integer> e : list) {
			if(e.getValue()>10 && count < len) {
				JSONObject o = new JSONObject();
				o.put("name", e.getKey());
				o.put("value", e.getValue());
				a.add(o);
				count +=1 ;
			}
		}
		return a;
	}
	/*
	 * 获取处理过的welfare字段内容的list
	 *  @param tag 为过滤的关键字
	 * */  
	public JSONArray getWelfare( String tag) throws IOException {
		ResultScanner rs = HbaseOpUtil.getRS(table, family, qualifier, tag);
		List<Map.Entry<String, Integer>> list = HbaseOpUtil.welfareSplit(rs, family, param, "count");
		JSONArray array = new JSONArray();
		for (Entry<String, Integer> e : list) {
			if (!e.getKey().equals("") && e.getValue()>500 ) {
				JSONObject ob = new JSONObject();
				ob.put("name",e.getKey());
				ob.put("value",  e.getValue());
				array.add(ob);
		}
		}
		return array;
	}
}
