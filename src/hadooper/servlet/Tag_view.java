package hadooper.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hadooper.hbase.*;
/**
 * Servlet implementation class Jobtag_Visualshow
 */
@WebServlet("/Tag_view")
public class Tag_view extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tag_view() {
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
		PrintWriter out = response.getWriter();
		//param是图表信息，tag是职位信息
		String param = request.getParameter("param");
		String tag = request.getParameter("tag");
		if (!tag.isEmpty() && !param.isEmpty()) {
			//要修改获取的json类型的话直接改get方法就可以了 getObj或getArr
			switch (param) {	
		case "salary" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getObj(tag));
			break;
		case "people" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getArr(tag));
			break;
		case "location" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getArr(tag));
			break;
		case "financing" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getArr(tag));
			break;
		case "edu" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getObj(tag));
			break;
		case "exp" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getObj(tag));
			break;
		case "industry" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getIndustry(tag));
			break;
		case "welfare" :
			out.println(new HbaseTag_scan(("tag_"+param), param).getWelfare(tag));
			break;
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
