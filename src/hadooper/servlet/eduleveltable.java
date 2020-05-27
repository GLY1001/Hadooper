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
 * Servlet implementation class eduleveltable
 */
@WebServlet("/eduleveltable")
public class eduleveltable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public eduleveltable() {
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
		//param->哪一种的学历情况，choice->哪一个表
		String param = request.getParameter("param");
		String choice = request.getParameter("choice");
		//按照选择的表进行模式匹配
		switch (choice) {
		case "eduleveltable_location_hbase":
			out.println(new Hbaseedu_scan(choice,param).getArr("location"));
			break;
		case "eduleveltable_jobtag_hbase":
			out.println(new Hbaseedu_scan(choice,param).getObj("jobtag", "salary", "tag"));
			break;
		case "eduleveltable_people_hbase":
			out.println(new Hbaseedu_scan(choice,param).getRadar());
			break;
		case "eduleveltable_experience_hbase":
			out.println(new Hbaseedu_scan(choice,param).getArr("explevel"));
			break;
		case "edulevelindustry":
			out.println(new Hbaseedu_scan(choice,param).getIndustry("industry" ));
			break;
		case "eduleveltable_financing_hbase":
			out.println(new Hbaseedu_scan(choice,param).getObj("financing","amount","financing" ));
			break;
		case "edulevelwelfare"	:
			out.println(new Hbaseedu_scan(choice,param).getWelfare("welfare" ));
			break;
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