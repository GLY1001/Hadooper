package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.hadoop.hive.ql.exec.UDF;

public class exp_level extends UDF {

	public String evaluate(String line) {
		 //匹配xx年的数据
		 String pattern = "(\\d+)";
		 Pattern r = Pattern.compile(pattern);
		 Matcher m = r.matcher(line);
		 //把它们对应放到各分区
	      if (m.find( )) {
	         int s = Integer.parseInt(m.group(1));
	         if(s<3){
	        	 return "1-3年";
	         }else if(s>=3&&s<5){
	        	 return "3-5年";
	         }else if(s>=5&&s<7){
	        	 return "5-7年";
	         }else if(s>=7&&s<10){
	        	 return "7-10年";
	         }else if(s>=10&&s<15){
	        	 return "10-15年";
	         }else if(s>=15){
	        	 return "15年+";
	         }
	      }
		if(line.contains("应届"))
			return "应届毕业生";
		if(line.contains("在读"))
			return "在读生";
		if(line.contains("无")||line.contains("不"))
			return "经验不限";
		return line;	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exp_level e = new exp_level();
		System.out.println(e.evaluate("经验不限"));
	}

}
