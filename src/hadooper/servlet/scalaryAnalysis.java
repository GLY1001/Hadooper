package hadooper.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hadooper.hbase.Hbaseother_scan;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class scalaryAnalysis
 */
@WebServlet("/scalaryAnalysis")
public class scalaryAnalysis extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scalaryAnalysis() {
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
		
		JSONObject obj = new JSONObject();
		JSONArray location = new Hbaseother_scan("locationSalary","location").getArray();
		JSONObject edulevel = new Hbaseother_scan("edulevelSalary","edulevel").getObj("edulevel","salary","edulevel");
		JSONObject jobtag = new Hbaseother_scan("jobtagSalary","jobtag").getObj("jobtag","salary","jobtag");
		
		obj.put("location", location);
		obj.put("edulevel", edulevel);
		obj.put("jobtag", jobtag);
		
		out.println(obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
