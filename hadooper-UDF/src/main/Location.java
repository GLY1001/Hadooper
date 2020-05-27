package main;

import org.apache.hadoop.hive.ql.exec.UDF;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Location extends UDF{
	
	public String evaluate(String line) {
		 //匹配xxx区
		 String prepattern = "(.+?区)";
	     Pattern r = Pattern.compile(prepattern);
	     Matcher m = r.matcher(line);
	     //把包含以下市区的名字直接映射到省名
	     if (m.find( )) {
	        String input = m.group(0);
			if(input.contains("兰州")) {
				return "甘肃";
			}else if(input.contains("乌鲁木齐")) {
				return "内蒙古";
			}else if(input.contains("内蒙古")) {
				return "内蒙古";
			}else if(input.contains("呼和浩特")) {
				return "内蒙古";
			}else if(input.contains("贵阳")) {
				return "贵州";
			}else if(input.contains("银川")) {
				return "宁夏";
			}else if(input.contains("新疆")) {
				return "新疆";
			}else if(input.contains("青海")) {
				return "青海";
			}else {
				String[] shanghai = {"上海","浦东","张江","杨浦区","工业园区","虹口区","闵行区","静安区","青浦区","宝山区","奉贤区","长宁区","徐汇区","黄浦区","嘉定","松江区","普陀区"};
				 for(String s:shanghai) {
					 if(input.contains(s)) {
						 return "上海";
					 }
				 }
				 String[] tianjin = {"天津","南开","和平区","开发区","滨海新区","东丽区","河西区"};
				 for(String s:tianjin) {
					 if(input.contains(s)) {
						 return "天津";
					 }
				 }
				 String[] guangdong = {"广东","横琴新区","中航沙河工业区","经济技术开发区","龙华","前海深港","罗湖区","福田区","海珠","花都","中山","南山区","江门","越秀","宝安","华南","白云","天河","龙岗","广州","罗岗","黄埔","顺德","佛山","清远","湛江","深圳","东莞","惠州","珠海","番禺","禅城","南沙","南海","肇庆","荔湾"};
				 for(String s:guangdong) {
					 if(input.contains(s)) {
						 return "广东";
					 }
				 }
				 String[] beijing = {"北京","海淀","经济开发区","门头沟区","通州区","东城区","丰台区","西城区","顺义区","朝阳","中关","亦庄","大兴","清华","怀柔","昌平"};
				 for(String s:beijing) {
					 if(input.contains(s)) {
						 return "北京";
					 }
				 }
				 String[] zhejiang = {"浙江","无锡","自贸试验区","慈溪","下沙","上城区","下城区","江干区","余杭区","萧山","杭州","温州","拱墅区","宁波","滨江区","福州","台州","嘉兴","义乌","西湖"};
				 for(String s:zhejiang) {
					 if(input.contains(s)) {
						 return "浙江";
					 }
				 }
				 String[] jiangsu = {"江苏","南京","江宁区","玄武区","宿城","秦淮区","红谷滩新区","南昌","雨花台区","浦口区","苏州","常州","鼓楼","栖霞","扬州"};
				 for(String s:jiangsu) {
					 if(input.contains(s)) {
						 return "江苏";
					 }
				 }
				 String[] fujian = {"福建","厦门","泉州","福州","仓山区","台江区","湖里区","思明区"};
				 for(String s:fujian) {
					 if(input.contains(s)) {
						 return "福建";
					 }
				 }
				 String[] liaoning = {"辽宁","大连","沈河区","沙河口区","沈阳","沈北新区"};
				 for(String s:liaoning) {
					 if(input.contains(s)) {
						 return "辽宁";
					 }
				 }
				 String[] anhui = {"安徽","合肥","蜀山区","经开区","芜湖","阜阳","颍州","滨湖新区"};
				 for(String s:anhui) {
					 if(input.contains(s)) {
						 return "安徽";
					 }
				 }
				 String[] hainan = {"海南","三亚","海口"};
				 for(String s:hainan) {
					 if(input.contains(s)) {
						 return "海南";
					 }
				 }
				 String[] hubei = {"湖北","武汉","洪山","江夏区","武昌区","东湖","武大","江岸区"};
				 for(String s:hubei) {
					 if(input.contains(s)) {
						 return "湖北";
					 }
				 }
				 String[] shandong = {"山东","市北区","青岛","黄岛","临沂","济南","历城区","济宁","烟台","潍坊","山东","崂山"};
				 for(String s:shandong) {
					 if(input.contains(s)) {
						 return "山东";
					 }
				 }
				 String[] shanxii = {"山西","太原"};
				 for(String s:shanxii) {
					 if(input.contains(s)) {
						 return "山西";
					 }
				 }
				 String[] shanxi = {"陕西","西安","长安","咸阳","雁塔区"};
				 for(String s:shanxi) {
					 if(input.contains(s)) {
						 return "陕西";
					 }
				 }
				 String[] hunan = {"湖南","长沙","天心区","开福区","岳麓区","衡阳","芙蓉区"};
				 for(String s:hunan) {
					 if(input.contains(s)) {
						 return "湖南";
					 }
				 }
				 String[] jilin = {"吉林","长春"};
				 for(String s:jilin) {
					 if(input.contains(s)) {
						 return "吉林";
					 }
				 }
				 String[] henan = {"河南","三门峡","郑州","二七区","开封","洛阳","郑东新区"};
				 for(String s:henan) {
					 if(input.contains(s)) {
						 return "河南";
					 }
				 }
				 String[] hebei = {"河北","邢台","廊坊","石家庄"};
				 for(String s:hebei) {
					 if(input.contains(s)) {
						 return "河北";
					 }
				 }
				 String[] jiangxi = {"江西","南昌"};
				 for(String s:jiangxi) {
					 if(input.contains(s)) {
						 return "江西";
					 }
				 }
				 String[] sichuan = {"成都","金水区","武侯区","金牛区","青羊","高新","四川","南岸区","锦江区","成华区","江阳区"};
				 for(String s:sichuan) {
					 if(input.contains(s)) {
						 return "四川";
					 }
				 }
				 String[] yunnan = {"昆明","云南"};
				 for(String s:yunnan) {
					 if(input.contains(s)) {
						 return "云南";
					 }
				 }
				 String[] chongqing = {"九龙坡区","江北","重庆","渝北"};
				 for(String s:chongqing) {
					 if(input.contains(s)) {
						 return "重庆";
					 }
				 }
				 String[] guangxi = {"广西","柳州","南宁","桂林","西乡塘区"};
				 for(String s:guangxi) {
					 if(input.contains(s)) {
						 return "广西";
					 }
				 }
				 String[] heilongjiang = {"黑龙江","哈尔滨"};
				 for(String s:heilongjiang) {
					 if(input.contains(s)) {
						 return "黑龙江";
					 }
				 }
				 		return null;
			  }
	      }else{
		      return null;
	      }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Location re = new Location();
		String input = "萍水西街80号优盘时代1号楼9楼";
		System.out.println(re.evaluate(input));
		
	}

}
