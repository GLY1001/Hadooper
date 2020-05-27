package hadooper.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Hbaseedu_scan {
	private Table table;
	private String param;
	private byte[] family = "f".getBytes();
	private byte[] qualifier = "edulevel".getBytes();
	//初始化获取hbase表实例以及保留param参数过滤器筛选使用
	public Hbaseedu_scan(String tablename, String param) throws IOException{
		table = HbaseOpUtil.getTable(tablename);
		this.param = param;
	}
	/*
	 * 获取柱状图数据 
	 * @param colname1|2 为hbase表的列限定符名分别为name value
	 * */
	public JSONObject getObj(String colname1, String colname2, String name) throws IOException{
		JSONArray a1 = new JSONArray();
		JSONArray a2 = new JSONArray();
		JSONObject object = new JSONObject();
		TreeMap<String, Double> tree = new TreeMap<String, Double>();
		ResultScanner result = HbaseOpUtil.getRS(table, family, qualifier, param);
		for(Result r:result) {
			String y = new String(r.getValue(family, colname1.getBytes())) ;
			Double x  = Double.parseDouble(new String(r.getValue(family, colname2.getBytes())));
				tree.put(y, x);
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(tree.entrySet());
		
		 // 通过比较器来实现排序 TreeMap按照value排序
      Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
          @Override
          public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
              // 降序排序
//              return o2.getValue().compareTo(o1.getValue());
              return o2.getValue()-o1.getValue()>0? 1:-1;
          }
      });     
      for (Entry<String, Double> e : list) {
      	a1.add(e.getKey());
      	a2.add(e.getValue());
      }
		object.put(name, a1);
		object.put("amount", a2);
        table.close();
		return object;
	}
	//饼图所需的数据 edu 为所需数据的列限定符参数
	public JSONArray getArr(String edu) throws IOException{
		JSONArray a = new JSONArray();
		ResultScanner result = HbaseOpUtil.getRS(table, family, qualifier, param);
		for(Result r:result) {
			JSONObject o = new JSONObject();
			o.put("name", new String(r.getValue(family, edu.getBytes())));
			o.put("value", Integer.parseInt(new String(r.getValue(family, "amount".getBytes()))));
			a.add(o);
		}
        table.close();
		return a;
	}
	//雷达图所需的数据
	public JSONArray getRadar() throws IOException{
		JSONArray objArray = new JSONArray();
		JSONObject object = new JSONObject();
		int[] arr = new int[5];
		ResultScanner result = HbaseOpUtil.getRS(table, family, qualifier, param);
		for(Result r:result) {
			switch(new String(r.getValue(family, "people".getBytes()))) {
				case "0-100人":
					arr[2] = Integer.parseInt(new String(r.getValue(family, "amount".getBytes())));
					break;
				case "500-1000人":
					arr[1] = Integer.parseInt(new String(r.getValue(family, "amount".getBytes())));
					break;
				case "1000-5000人":
					arr[4] = Integer.parseInt(new String(r.getValue(family, "amount".getBytes())));
					break;
				case "5000-10000人":
					arr[0] = Integer.parseInt(new String(r.getValue(family, "amount".getBytes())));
					break;
				case "10000以上":
					if(Integer.parseInt(new String(r.getValue(family, "amount".getBytes())))==14)
						continue;
					arr[3] = Integer.parseInt(new String(r.getValue(family, "amount".getBytes())));
					break;
			}
		}
			object.put("name", param);
			object.put("value", arr);
			objArray.add(object);
	        table.close();
		return objArray;
	}
	/*
	 * 获取处理过的industry字段内容的list
	 *  @param param 过滤的关键字
	 *  @param edu 获取数据
	 * */ 
	public JSONArray getIndustry( String edu ) throws IOException {
		ResultScanner rs = HbaseOpUtil.getRS(table, family, qualifier , param);
		List<Map.Entry<String, Integer>> list = HbaseOpUtil.industrySplit(rs, family, edu, "amount");
		JSONArray a = new JSONArray();
		int len = list.size();
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
	 *  @param param 过滤的关键字
	 *  @param edu 获取数据
	 * */ 
	public JSONArray getWelfare( String edu ) throws IOException {
		ResultScanner rs = HbaseOpUtil.getRS(table, family, qualifier, param);
		List<Map.Entry<String, Integer>> list = HbaseOpUtil.industrySplit(rs, family, edu, "amount");
		JSONArray array = new JSONArray();
		for (Entry<String, Integer> e : list) {
			if (e.getValue() > 500 && !e.getKey().equals("")) {
				JSONObject ob = new JSONObject();
				ob.put("name",e.getKey());
				ob.put("value",  e.getValue());
				array.add(ob);
		}
		}
		return array;
	}
}
