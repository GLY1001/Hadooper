package main;

import java.util.*;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class JobInfoSeg extends GenericUDTF{
	private static String[] pointword1 = {
			"ai","c","caffe","cntk","deepleanig","flink","flume",
			"go","gensim","hadoop","hive","java","kafka",
			"nlp","numpy","mahout","mxnet","mysql","opengl",
			"ocr","pca","python","pytorch","perl",
			"r","scipy","scikit-learn","storm","scala","sparkml(lib)*?","sql",
			"sqlserver","svm","tensorflow","theano","torch"
			};
	private static String[] pointword2 = {
			"图像分析","图像理解","计算机视觉","机器视觉","语音处理",
			"人脸识别","线性回归","逻辑回归","贝叶斯","决策树",
			"随机森林","神经网络","聚类","文本挖掘","数学模型",
			"物体检测","物体识别","场景理解","行为分析",
			"手势识别","神经网络","语义分割","算法设计",
			"云计算","区块链","指纹识别","数据结构","模型优化",
			"协同过滤","机器翻译","人机对话","自动驾驶"
			};
	private String job_tag ;
	@Override
	public void close() throws HiveException {
		// TODO Auto-generated method stub		
	}
	@Override
	//udtf运行的主方法
	public  void process(Object[] values) throws HiveException {
		// TODO Auto-generated method stub
		String text = values[0].toString().trim();
		this.job_tag = values[1].toString().trim();
		if(text.equals("")&&text.isEmpty()&&text.equals(" ")) {
			return;
		}else {
			//调用分词方法
			anjs(text);
		}
	}
	//分词开始之前，动态加载自定义词典，添加词典直接在pointword2数组中添加即可
	public void loadLib() {
		for (String s : pointword2) {
			//3000 time
			UserDefineLibrary.insertWord(s, "n", 3000);
		}
	}
	//分词主方法
	public void anjs(String info) throws HiveException{
		List<Term> value;
		//加载词典
		loadLib();
		//分词
		value = ToAnalysis.parse(info).getTerms();
		for (Term t : value) {
			String y = t.getName();
			for(String x : pointword1) {
				
				if (y.equals(x)) {
					forward(new String[] {job_tag,x});
				}
			}
		for(String x : pointword2) {
				if (y.equals(x)) {
					forward(new String[] {job_tag,x});
				}
			}
		}
	}
	
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		//定义列名
		List<String> fieldname = new ArrayList<String>();
		//定义列数据类型
		List<ObjectInspector> fieldOIS = new ArrayList<ObjectInspector>();
		//添加列名
		fieldname.add("job_tag");
		fieldname.add("point_word");
		//添加数据类型
		fieldOIS.add(PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING));
		fieldOIS.add(PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING));
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldname, fieldOIS);
		
	}
}
