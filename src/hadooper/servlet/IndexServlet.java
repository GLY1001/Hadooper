package hadooper.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import hadooper.hbase.*;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String model = request.getParameter("model");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Connection conn = (Connection)session.getAttribute("conn");
		Table pointwords = null  ;
		JSONObject obj = new JSONObject();
//		System.out.println(conn==null);
		if (conn == null) {
			conn = HbaseOpUtil.getConnection();
			session.setAttribute("conn", conn);
		}
		//返回options选框的数据
		String param = request.getParameter("param");
		/*
		 * String[] splits = param.split("-"); if(splits.length>1) {
		 * if(splits[0].equals("a")) { out.println(new
		 * HbaseIndex_scan("pointwords","cf","tag").getFilterArr(splits[1], "pointword",
		 * "number", 9)); }else { out.println(new
		 * HbaseIndex_scan("pointwords","cf","tag").getFilterArr(splits[1], "pointword",
		 * "number", 20)); } }
		 */
		switch (model) {
		case "four1":
			Table jobtagparam = conn.getTable(TableName.valueOf("top10job_tag"));
			Table jobnameparam = conn.getTable(TableName.valueOf("top20job_name"));
			Table salaryparam = conn.getTable(TableName.valueOf("top10SalaryOfTag"));
			Table avg_salaryparam = conn.getTable(TableName.valueOf("top5SalaryOfName"));
			obj.put("jobtagparam", new HbaseIndex_scan(jobtagparam).getObj("job_tag", "number" ,9));
			obj.put("jobnameparam", new HbaseIndex_scan(jobnameparam).getArr("job_name", "number", Integer.MAX_VALUE));
			obj.put("salaryparam", new HbaseIndex_scan(salaryparam,"f").getObj("job_tag", "job_salary", 100.0d));
			obj.put("avg_salaryparam", new HbaseIndex_scan(avg_salaryparam).getArr("job_name", "salary_avg", 100.0d));
			out.println(obj);
			break;
		case "four2":
			Table pointwordparam = conn.getTable(TableName.valueOf("top10pointword"));
			Table reqPeople = conn.getTable(TableName.valueOf("edulevel_people_hbase"));
			Table index_industry = conn.getTable(TableName.valueOf("index_industry"));
			Table financingPie = conn.getTable(TableName.valueOf("financingPie"));
			obj.put("index_industry",new HbaseIndex_scan(index_industry, "f", "").getIndustry("industry" ,"count"));
			obj.put("financingPie",new HbaseIndex_scan(financingPie).getArr("financing", "amount", Integer.MAX_VALUE));
			obj.put("pointwordparam", new HbaseIndex_scan(pointwordparam,"f","tag").getObj("pointword", "number", 9));
			obj.put("reqPeople", new HbaseIndex_scan(reqPeople).getRadar());
			out.println(obj);
			break;
		case "single10":
			pointwords = conn.getTable(TableName.valueOf("pointwords"));
			out.println(new HbaseIndex_scan(pointwords,"f","tag").getFilterArr(param, "pointword", "number", 9));
			break;
		case "single":
			pointwords = conn.getTable(TableName.valueOf("pointwords"));
			out.println(new HbaseIndex_scan(pointwords,"f","tag").getFilterArr(param, "pointword", "number", 20));
			break;
		}
//		switch (param) {
//		case "jobtagparam" :
//				out.println(new HbaseIndex_scan("top10job_tag").getObj("job_tag", "number" ,9));
//			break;
//		case "jobnameparam" :
//				out.println(new HbaseIndex_scan("top20job_name").getArr("job_name", "number", Integer.MAX_VALUE));
//			break;
//		case "salaryparam" :
//				out.println(new HbaseIndex_scan("top10SalaryOfTag","f").getObj("job_tag", "job_salary", 100.0d));
//			break;
//		case "avg_salaryparam" :
//				out.println(new HbaseIndex_scan("top5SalaryOfName").getArr("job_name", "salary_avg", 100.0d));
//			break;
//		case "pointwordparam" :
//				out.println(new HbaseIndex_scan("pointwords").getObj("pointword", "number", 9));
//			break;
//		case "reqPeople" :
//				out.println(new HbaseIndex_scan("edulevel_people_hbase").getRadar());
//			break;
//		case "industry" :
//				out.println(new HbaseIndex_scan("index_industry", "f", "").getIndustry(param ,"count"));
//			break;
//		case "financing" :
//				out.println(new HbaseIndex_scan("financingPie").getArr(param, "amount", Integer.MAX_VALUE));
//			break;
//		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
