package hadooper.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Hbaseother_scan {
	
	private Table table;
	private String param;
	private byte[] family = "f".getBytes();
	//这里不需要用到
	private byte[] qualifier = "".getBytes();
	/*
	 * @param tablename hbase表名
	 * @param param是数据需要的列限定符
	 * */
	public Hbaseother_scan(String tablename, String param) throws IOException{
		table = HbaseOpUtil.getTable(tablename);
		this.param = param;
	}
	//中国地图数据
	public JSONArray getArray() throws IOException {
		JSONArray objArray = new JSONArray();
		Scan scan = new Scan();
		ResultScanner result = table.getScanner(scan);
		for(Result r:result) {
			JSONObject object = new JSONObject();
			object.put("name", new String(r.getValue(family, param.getBytes())));
			object.put("value", Double.parseDouble(new String(r.getValue(family, "salary".getBytes()))));
			objArray.add(object);
		}
        table.close();
		return objArray;
	}

	//柱状图数据
	public JSONObject getObj(String b1, String b2, String c) throws IOException{
		JSONArray a1 = new JSONArray();
		JSONArray a2 = new JSONArray();
		JSONObject object = new JSONObject();
		TreeMap<String, Double> tree = new TreeMap<String, Double>();
		ResultScanner result = HbaseOpUtil.getRS(table, family, qualifier, param);
		for(Result r:result) {
			String y = new String(r.getValue(family, b1.getBytes())) ;
			Double x  = Double.parseDouble(new String(r.getValue(family, b2.getBytes())));
				tree.put(y, x);
		}
		List <Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(tree.entrySet());
		
		 // 通过比较器来实现排序 TreeMap按照value排序
      Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
          @Override
          public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
              // 降序排序
              return o2.getValue()-o1.getValue()>0? 1:-1;
          }
      });     
      for (Entry<String, Double> e : list) {
    	  if(e.getValue() <= 0) continue;
      	a1.add(e.getKey());
      	a2.add(e.getValue());
      }
		object.put(c, a1);
		object.put("salary", a2);
        table.close();
		return object;
	}


}
