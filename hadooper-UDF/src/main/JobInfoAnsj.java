package main;

import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.impl.FilterRecognition;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class JobInfoAnsj extends GenericUDTF{
	/*private static String[] pointword1 = {
			"ai","c","caffe","cntk","deepleanig","flink","flume",
			"go","gensim","hadoop","hive","java","kafka",
			"nlp","numpy","mahout","mxnet","mysql","opengl",
			"ocr","pca","python","pytorch","perl",
			"r","scipy","scikit-learn","storm","scala","sparkml(lib)*?","sql",
			"sqlserver","svm","tensorflow","theano","torch"
			};*/
//	select job_info from jobtable where job_tag = "人工智能" limit 300;
	private static String[] pointword2 = {
			"图像分析","图像理解","计算机视觉","机器视觉","语音处理","深度学习",
			"人脸识别","线性回归","逻辑回归","贝叶斯","决策树","机器学习",
			"随机森林","神经网络","聚类","文本挖掘","数学模型","推荐系统",
			"物体检测","物体识别","场景理解","行为分析","用户画像","量化投资",
			"手势识别","神经网络","语义分割","算法设计",
			"云计算","区块链","指纹识别","数据结构","模型优化",
			"协同过滤","机器翻译","人机对话","自动驾驶"
			};
	private static String[] stopwords = {
			"工作","职责","岗位","行业","学位","经验","博士","硕士","s","ip","ka",
			"ppt","vip","ebay","google","com","cn","crm","it","kpi","p","l","o","www",
			"金融","市场","股票","交易","职称","学历","职责","公司","行业","一家","上进心",
			"业务","产品","团队","技术","资源","专利","压力","客户","要求",
			"专业","文本","理解","认识","兴趣","汽车","家","面","量","需求",
			"部门","软件","资格","质量","课程","语言","语音","计算机","规划",
			"补贴","薪资","范围","能力","背景","问题","项目","职能","类别","微信",
			"系统","算法","平台","模型","用户","图像","员工","经理","福利","目标","网络",
			"企业","工程师","策略","流程","工","计划","人","人力","信息","方案",
			"基础","数学","关键字","领域","精神","线","工具","地址","互联网","后台",
			"数据","职位","知识","智能","体系","框架","商","实际","本科","架构",
			"地图","方法","广告","内容","意识","责任心","报告","工程","渠道","方向",
			"大专","模式","环境","电子","专员","店铺","时间","待遇","人员","品牌","网站",
			"力","关系","效果","核心","路","商务","理论","科技","区","场景","情况","店",
			"媒体","领导","商业","方面","任务","对数","底薪","文档","提成","电话",
			"网","电","建议","指标","技能","月","思维","海量","原理","风险","中心","绩效",
			"宝","视频","奖金","报表","代码","逻辑","财务","职业","楼","国家","性能",
			"功能","助理","标准","机会","商品","过程","整体","经历","特征","结果","决策",
			"视觉","区域","行为","保险","方式","b","医疗","战略","结构","主流","业绩","模块",
			"国际","人才","总监","价值","功底","年度","制度","奖","大厦","生日","空间","深度",
			"个人","大学","节日","上级","a","趋势","资料","风","集团","车","论文","库",
			"游戏","关键","信号","科学","程序","app","条件","k","机器人","顾客","语义",
			"图谱","产业","红包","梦想","伯乐","房源","月薪","城市","智慧","代表",
			"云","教学","社区","顾问","中经理","猫","文案","成本","账户","技巧","人事",
			"流量","规则","文字","会计","外贸","库存","行政","对手","创意","预算","动态",
			"文化","简历","关键词","合同","年终奖","编制","订单","编辑","货","供应商","货品",
			"店面","经济","交通","门店","工资","年假","转化率","岗","性格","设备","代表",
			"机制","规定","年龄","银行","物流","地点","资金","款","季度","微博","费用",
			"政策","毕业生","会员","理","总经理","月度","效率","仓库","激情","招","机构","图","会议","全日制",
			"思路","搜索引擎","推荐系统","进度","硬件","有限公司","医学","d","重点","氛围","水平",
			"责任感","生物","数字","心","工业","室","敏感度","竞争力","成果","态度","编码","地铁",
			"用户画像","责任","意见","习惯","手机","小时","神","学员","伙伴","专家","协议","全国",
			"广场","特点","研究生","理念","文献","形象","文件","案例","原型","依据","信","手段","事业",
			"总部","洞察力","引擎","专科","图形","津贴","全球","周期","公众","手","成员","实战","官","团",
			"状况","路线","规律","素质","导向","科研","茶","生命","主导","传统","热点","收入","信贷",
			"亲和力","学术","负责人","粉丝","礼品","品质","值","页面",	"管理员","传感器","社交","价","事件",
			"阶段","通道","服务器","院校","图片","生态","同事","通讯","头","房","分公司","环节","学生",
			"影响力","城","优势","信用","gpu","新闻","社","婚假","满意度","住房","价格","公积金","运动",
			"门","人际","专题","物理","终端","词","车辆","资产","路径","营","可行性","电脑","病假",
			"材料","政府","商城","机械","部分","心理","雷达","表","布","措施","基金","卡","社会","全勤",
			"海","期刊","服装","新区","主","素养","园区","联系人","大道","礼物","形式","证券",
			"事宜","主题","自动驾驶","芯片","工作制","零食","试用期","中介","事务","对象","精英",
			"见解","报","理工科","实体","人脸","贷款","概念","科","高薪","讲师","孩子","账",
			"弹性","细节","特性","组件","物料","标签","潜力","医药","青年","定义","街","时代",
			"主动性","档案","实验室","为人","桥","版本","地区","销量","音频","教练","底层","邮箱",
			"工艺","激光","天河","设计师","掌","消费者","角度","论坛","单位","课","公关","事","针对性","老师","房产",
			"邮费","物体","人体","世界","狗","统计学","法规","产假","美术","作品","平面","风格","界面","h","艺术","色彩","网页","海报","banner","素材",
			"插画","创造力","相机","几何","情感","词性","句法","意图","挑战性","声学","解码器","普通话","口齿","接口","法律",
			"教师","干部","理想","学院","实习生","疑问","公寓","外汇","盈利","事项","品类","餐饮","amazon","账号","字","新品","职业道德","期货","税务",
			"工伤","行情","性别","布局","证书","集体","官方","食品","小姐","话题","业务员","礼金","操守","数量","男女","酒店","信用卡","逻辑性","班",
			"假日","交易员","大小","利润","函数","办公室","媒介","分红","表现力"
			};
//select * from segtable where point_word="地区" ;
//	图像				数据挖掘		机器学习		机器视觉
//	add jar /home/hadoop/document/hadooper.jar;
//	create temporary function ansj as "main.JobInfoAnsj";
	private String job_tag ;
	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub		
	}
	@Override
	public  void process(Object[] values) throws HiveException {
		// TODO Auto-generated method stub
		String text = values[0].toString().trim();
		//String model = values[1].toString();
		this.job_tag = values[1].toString().trim();
		if(text.equals("")&&text.isEmpty()&&text.equals(" ")) {
			return;
		}else {
			anjs(text);
		}
	}
	//分词开始之前，动态加载自定义词典，添加词典直接在pointword2数组中添加即可
	public static FilterRecognition loadLib() {
		for (String s : pointword2) {
			//3000 time
			UserDefineLibrary.insertWord(s, "n", 3000);
		}
		List<String> list = new ArrayList<String>(stopwords.length);
		Collections.addAll(list, stopwords);
		
		FilterRecognition re = new FilterRecognition();
		
		re.insertStopWords(list);
		return re;
	}
	//分词主方法
	public void anjs(String info) throws HiveException{
		List<Term> value;
//		Pattern p ;
//		Matcher m ;
		//加载词典,过滤停用词
		FilterRecognition re = loadLib();
		//分词
		value = ToAnalysis.parse(info).recognition(re).getTerms();
		for (Term t : value) {
			if (t.getNatureStr().equals("n")) {
				forward(new String[] {job_tag,t.getName()});
			}else if(t.getNatureStr().equals("en") && t.getName().length()<8) {
				forward(new String[] {job_tag,t.getName()});
			}
			/*String y = t.getName();
			for(String x : pointword1) {
				p = Pattern.compile("(^"+x+"$)");
				m = p.matcher(y.toLowerCase());
				if(m.find()) {
					forward(new String[] {job_tag,m.group(1)});
//					System.out.println(m.group(1));
				}	
			}for(String x : pointword2) {
				p = Pattern.compile("(^"+x+"$)");
				m = p.matcher(y.toLowerCase());
				if(m.find()) {
					forward(new String[] {job_tag,m.group(1)});
//					System.out.println(m.group(1)+job_tag);
				}	
			}*/
		}
	}
	
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		//列名
		List<String> fieldname = new ArrayList<String>();
		//列数据类型
		List<ObjectInspector> fieldOIS = new ArrayList<ObjectInspector>();
		//添加列名
		fieldname.add("job_tag");
		fieldname.add("point_word");
//		fieldname.add("word_en");
		//添加数据类型
		fieldOIS.add(PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING));
		fieldOIS.add(PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING));
//		fieldOIS.add(PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING));
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldname, fieldOIS);
	}

	public static void main (String[] args) throws Exception{
//		String sentence = "工作职责：1.基于深度学习相关应用的研发工作，包括智能投顾、机器学习和量化投资、用户画像、推荐系统等智能化应用的基础数据处理、算法优化等2.根据行业趋势和公司战略发展规划，推动公司智能投顾领域中人工智能相关的研究工作。岗位要求：1．硕士及以上学历，博士学位优先。具有3年以上工作经验，具备扎实的数学基础和机器学习算法功底，熟悉决策树、随机森林、协同过滤、SVM、回归等相关机器学习算法2．具有很强的钻研能力，想象力和创造力3．具有金融市场的广度知识及股票市场研究经验，了解技术分析和基本面分析，了解股票交易算法及各种金融指标的应用。4．精通Python编程语言（Numpy,Scipy,pandas,scikit-learn,gensim）5．熟悉神经网络理论，熟悉CNN,RNN,LSTM等原理，至少能够熟练使用Caffe,TensorFlow,Keras,PyTorch其中任一框架进行建模和训练6．职称可以根据工作经验及专业水准进行调节";
//		String sentence1 = "能力AIIPSWDesignEngineerJobLocation:Shenzhen/Shanghai/BeijingJobDescriptionTheSoftwareEngineerwillbepartofasoftwaredevelopmentteamthatdevelopsanddeploysstateoftheartsoftwareforArtificialIntelligence(AI)relatedIP.Theengineerwillbeexpectedto •Enablemachinelearningalgorithmsand/orapplicationsonarmAIproductsforrelatedthestateofthearttechnologiessuchascomputervision,sensorfusion,machinelearning,objecttrackingetc.•Conductverificationonefficientmachinelearningmethods-supervisedlearning,unsupervised,reinforcement,and/ordeeplearningforapplicationsofobjectclassification,detectionandrecognition.•Developand/orprototypesoftwaresolutions(withallneededsoftwarestacks)fortypicalusagescenarioslikeinvideo/speech/textanalyticgeneratedbymultiplesensorsandtrackingsystems,achievingcompetitiveperformance. Education&Qualifications Masters(MS)orPhDinElectricalEngineeringorComputerScience/Engineering,focusedonComputerVision,ArtificialIntelligence,MachineLearning,orrelatedtechnicalfield. DesirableSkillsandExperience:•ExpertprogrammingskillsinC/C++;•SolidprogrammingexperienceinLinux,RTOS;•WorkingknowledgeofCPU,GPUorDSParchitecture;•Fastprototypingskills,includingcomprehensivefeatureintegrationduringallcyclesofdevelopment;•KnowledgeinDeepLearningNeuralNetworks(forclassificationanddetection)and/orinoneoffollowingtechniques,logisticregression,SVM,decisiontrees,andrandomforests;•Experienceinarchitecturespecificsoftwareoptimization. PersonalSkillsWeareproudtohaveasetofbehaviorsthatreflectouruniquecultureandguideourdecisions,defininghowweworktogethertodefyordinaryandshapeextraordinary.•Strongdesiretodeliveryresultandwinbusiness.•Positive,“cando”attitude.•Self-motivated,dependable,andprompt.•Flexible,willingtoadapttochangingresponsibilitiesandassignments.•Goodcommunicator,personable.•Capableofworkingasateamplayer.•Ethical,honest,andtrustworthy.•Abletoworkandcommunicateatadetailedorahighlevel,assituationrequires.•SufficientinEnglish";
//		
//		FilterRecognition re = loadLib();
//		re.insertStopWord("工作","职责","岗位","行业","学位","经验","博士","硕士","金融","市场","股票","交易","职称","学历","职责","公司");
//		
//		List<Term> value = ToAnalysis.parse(sentence1).recognition(re).getTerms();
//		System.out.println(value);
//		for (Term t : value) {
//			if (t.getNatureStr().equals("n")) {
//				System.out.println(t);
//			}else if(t.getNatureStr().equals("en") && t.getName().length()<10) {
//				System.out.println(t);
//			}
//		}
		//		new JobInfoAnsj().process(new String [] {"工作职责：1.基于深度学习相关应用的研发工作，包括智能投顾、机器学习和量化投资、用户画像、推荐系统等智能化应用的基础数据处理、算法优化等2.根据行业趋势和公司战略发展规划，推动公司智能投顾领域中人工智能相关的研究工作。岗位要求：1．硕士及以上学历，博士学位优先。具有3年以上工作经验，具备扎实的数学基础和机器学习算法功底，熟悉决策树、随机森林、协同过滤、SVM、回归等相关机器学习算法2．具有很强的钻研能力，想象力和创造力3．具有金融市场的广度知识及股票市场研究经验，了解技术分析和基本面分析，了解股票交易算法及各种金融指标的应用。4．精通Python编程语言（Numpy,Scipy,pandas,scikit-learn,gensim）5．熟悉神经网络理论，熟悉CNN,RNN,LSTM等原理，至少能够熟练使用Caffe,TensorFlow,Keras,PyTorch其中任一框架进行建模和训练6．职称可以根据工作经验及专业水准进行调节","123"});
		
	}
}
