package main;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;

public class UDFCollection extends UDF{
	//rawkeymaker
	public Integer evaluate(String str) {
		return str.hashCode() & Integer.MAX_VALUE ;
	}
	
	/*	清洗job_salary字段 
	 * 	@param mod是模式选择
	 * 	@param str是hive传入的信息
	 * */
	public double evaluate(String str,String mod) {

		Matcher m;
		switch (mod) {
		case "low" :
			//匹配 #####-#####元/月
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.*)元/月").matcher(str);
			if (m.find()) {	
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))*0.001));
			}
			//匹配 #####-#####千(以上|以下)*/月
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.*)千(以上|以下)*/月").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))));
			}
			//匹配 #####-#####万(以上|以下)*/月			
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.+)万(以上|以下)*/月").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))*10));
			}
			//匹配 #####-#####万(以上|以下)*/年	
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.+)万(以上|以下)*/年").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))*10/12));
			}
			//匹配 #####-#####万	
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.+)万$").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))*10/12));
			}
			//匹配 #####k-#####k	
			m = Pattern.compile("(\\d*\\.*?\\d*)k-").matcher(str.toLowerCase());
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))));
			}
			//匹配 面议
			m = Pattern.compile("面(.)").matcher(str);
			if (m.find()) {
				return 0.0;
			}
			//匹配 #####-#####
			m = Pattern.compile("(\\d*\\.*?\\d*)-(.+)$").matcher(str);
			if (m.find()) {
				return  Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(1))*0.001));
			}
			//匹配 ######-#####元/天
			m = Pattern.compile("(\\d*\\.*?\\d*)元/天").matcher(str);
			if (m.find()) {
				return 0.1;
			}
			break;
		case "high" :
			//匹配 #####-#####元/月
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)元/月").matcher(str);
			if (m.find()) {	
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))*0.001));
			}
			//匹配 #####-#####千(以上|以下)*/月
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)千(以上|以下)*/月").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))));
			}
			//匹配 #####-#####万(以上|以下)*/月	
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)万(以上|以下)*/月").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))*10));
			}
			//匹配 #####-#####万(以上|以下)*/年	
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)万(以上|以下)*/年").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))*10/12));
			}
			//匹配 #####-#####万	
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)万$").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))*10/12));
			}
			//匹配 #####k-#####k	
			m = Pattern.compile("(\\d*\\.*?\\d*)k-(\\d*\\.*?\\d*)k").matcher(str.toLowerCase());
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))));
			}
			//匹配 面议
			m = Pattern.compile("面(.)").matcher(str);
			if (m.find()) {
				return 0.0;
			}
			//匹配 #####-#####
			m = Pattern.compile("(\\d*\\.*?\\d*)-(\\d*\\.*?\\d*)$").matcher(str);
			if (m.find()) {
				return Double.parseDouble(new DecimalFormat("#.#").format(Double.parseDouble(m.group(2))*0.001));
			}
			//匹配 ######-#####元/天
			m = Pattern.compile("(\\d*\\.*?\\d*)元/天").matcher(str);
			if (m.find()) {
				return 0.1;
			}
			break;
		}	
		return 0.2;
	}
	/*
	 * @param company_people 清洗
	 * @param i 是为了区分几个函数没有具体意义
	 * @param people是hive传进去的字段
	 * 
	 * */
	public String evaluate(String people, int i) {
		//定义正则匹配
		Pattern  p = Pattern.compile("(\\d+)人");
		Matcher m = p.matcher(people);
		int val = 0;
		//匹配判断
		if (m.find()) {
			val = Integer.parseInt(m.group(1));
		}
		//将正则出来的值进行分类
		if (val != 0 ) {
			if (val <= 100) {
			return "0-100人";	
			}
			if (100 >= val && val <= 500) {
				return "100-500人";	
				}
			if (500 >= val && val <= 1000) {
				return "500-1000人";	
				}
			if (1000 >= val && val <= 5000) {
				return "1000-5000人";	
				}
			if (5000 >= val && val <= 10000) {
				return "5000-10000人";	
				}
			if(val == 10000)
				return "10000以上";
		}	
		return null;
	}

}
