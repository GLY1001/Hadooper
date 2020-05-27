package main;

import org.apache.hadoop.hive.ql.exec.UDF;

public class edu_level extends UDF{
	
	public String evaluate(String line) {
		//去掉空值
		if(line.equals("")) {
			return null;
		}
		//把类是的学历形容词放到一起
		if(line.substring(0, 1).equals("招")||line.contains("其他")||line.contains("不限")) {
			return "学历不限";
		}else if(line.equals("本科")) {
			return "本科及以上";	
		}else if(line.equals("硕士")) {
			return "硕士及以上";	
		}else if(line.contains("中专")||line.contains("中技")) {
			return "中专/中技及以上";	
		}else if(line.contains("大专")) {
			return "大专及以上";	
		}else if(line.contains("职高")) {
			return "高职";	
		}else if(line.contains("初中")) {
			return "初中及以上";	
		}else if(line.substring(0, 2).equals("学历")) {
			if(line.substring(3).equals("不限"))
				return "学历不限";
			if(line.substring(3).equals("本科"))
				return "本科及以上";
			if(line.substring(3).equals("硕士"))
				return "硕士及以上";
			return line.substring(3);
		}
		return line;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		edu_level e = new edu_level();
		System.out.println(e.evaluate("本科"));
	}

}
