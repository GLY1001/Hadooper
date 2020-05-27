package hadooper.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class HbaseOpUtil {
	private static Configuration conf;
	private static Connection conn;
	private static Table table;
	//对象初始化
	public HbaseOpUtil() throws IOException {
		init();
	}
	//获取配置文件内容
	private static void init() throws IOException {
		conf = HBaseConfiguration.create();
		conn = ConnectionFactory.createConnection(conf);
	}
	public static Connection getConnection() throws IOException {
		init();
		return conn;
	}
	//主要使用方法 ，获取相应的表 ，tablename为表名
	public static Table getTable(String tablename) throws IOException{
		init();
		table = conn.getTable(TableName.valueOf(tablename));
		return table;
	}
	/*
	 * 获取扫描数据集
	 * @param table Table实例
	 * @param family 列族名的字节数组
	 * @param qualifier 列限定符名的字节数组
	 * @param param 过滤的关键值
	 * */
	public static ResultScanner getRS(Table table,byte[] family, byte[] qualifier, String param) 
			throws IOException {
		Scan scan = new Scan();
		Filter filter = new SingleColumnValueFilter(
				family,
				qualifier,
				CompareOp.EQUAL,
				new BinaryComparator(param.getBytes())
				);
		scan.setFilter(filter);
		return table.getScanner(scan);
	}
	/*
	 * 获取扫描数据集
	 * @param table Table实例
	 * @param family 列族名的字节数组
	 * @param q1 列限定符名的字节数组
	 * @param param 过滤的关键值
	 * @param param1 过滤的关键值
	 * */
	public static ResultScanner getRS_AND(Table table,byte[] family, byte[] q1,byte[] q2, String param ,String param1) 
			throws IOException {
		Scan scan = new Scan();
		List<Filter> flist = new ArrayList<Filter>();
		Filter filter = new SingleColumnValueFilter(
				family,
				q1,
				CompareOp.EQUAL,
				new BinaryComparator(param.getBytes())
				);
		Filter filter1 = new SingleColumnValueFilter(
				family,
				q2,
				CompareOp.EQUAL,
				new BinaryComparator(param1.getBytes())
				);
		flist.add(filter);
		flist.add(filter1);
		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,flist);
		scan.setFilter(filterList);
		return table.getScanner(scan);
	}
	/*
	 * 返回处理过的welfare字段内容的list
	 * @param rs ResultScanner实例
	 * @param family 列族名的字节数组
	 *  @param colname1|2 为hbase表的列限定符名分别为name value
	 * */
	public static List<Map.Entry<String, Integer>> welfareSplit(ResultScanner rs, byte[] family, String colname1, String colname2) throws IOException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		String split = ",|/|、|，";
		for (Result r : rs) {
				String x = new String(r.getValue(family, colname1.getBytes()));
				int y = Integer.parseInt(new String(r.getValue(family, colname2.getBytes())));
					for (String s : x.split(split)) {
						map.put(s,(map.get(s)!=null?y+map.get(s):y));
					}				
		}
		List <Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());		
		 // 通过比较器来实现排序 TreeMap按照value排序
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
          @Override
          public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
              // 降序排序
              return o2.getValue().compareTo(o1.getValue());
          }
		});
		return list;
	}
	/*
	 * 返回处理过的industry字段内容的list
	 * @param rs ResultScanner实例
	 * @param family 列族名的字节数组
	 *  @param colname1|2 为hbase表的列限定符名分别为name value
	 * */
	public static List<Entry<String, Integer>> industrySplit(ResultScanner rs, byte[] family, String colname1, String colname2 ) throws IOException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		String split = ",|/|、|，";
		for (Result r : rs) {
				String x = new String(r.getValue(family, colname1.getBytes()));
				int y = Integer.parseInt(new String(r.getValue(family, colname2.getBytes())));
				//定义正则匹配器，先获取字段内容中括号内的部分,拼接成新的字段
					Matcher m = Pattern.compile("(.+)[\\(（](.+)[\\)）]").matcher(x);
					if (m.find()) {
						//获取括号内的内容
						String[] splits = m.group(1).split(split);
						String b = m.group(2);
						for(String z : b.split(split)) {
							//拼接形成新字段，并传入hashmap
							String name =splits[splits.length-1]+"-"+z;
							map.put(name,(map.get(name)!=null?y+map.get(name):y));
						}
						for(String z : b.split(split)) {
							//传入括号外的正常字段
							map.put(z,(map.get(z)!=null?y+map.get(z):y));
						}
					}else {
						//如果没有括号直接分隔字段内容传入
						for (String s : x.split(split)) {
						map.put(s,(map.get(s)!=null?y+map.get(s):y));
				}
				}			
		}
		List<Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());		
		 // 通过比较器来实现排序 TreeMap按照value排序
      Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
          @Override
          public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
              // 降序排序
              return o2.getValue().compareTo(o1.getValue());
          }
      });
      return list;
	}
}
