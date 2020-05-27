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

import hadooper.hbase.Hbase2JSON;
import hadooper.hbase.HbaseOpUtil;
import net.sf.json.JSONObject;

import org.apache.hadoop.hbase.client.*;

/**
 * Servlet implementation class OnDashServlet
 */
@WebServlet("/OnTagServlet")
public class OnTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnTagServlet() {
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
		String model = request.getParameter("model");
		String location = request.getParameter("location");
		String param = request.getParameter("param");
		HttpSession session = request.getSession();
		Connection conn = (Connection)session.getAttribute("conn");
//		System.out.println(conn==null);
		if (conn == null) {
			conn = HbaseOpUtil.getConnection();
			session.setAttribute("conn", conn);
		}

		JSONObject obj = new JSONObject();
		Table tag_salary = conn.getTable(TableName.valueOf("tag_location_salary"));
		Table edu_salary = conn.getTable(TableName.valueOf("tag_edu_salary"));
		Table tag = conn.getTable(TableName.valueOf("tag_location"));
		Table edu = conn.getTable(TableName.valueOf("tag_edu_location"));
		switch (model) {
		case "tag":
			obj.put("tag_salary",new Hbase2JSON().getFilter2O(tag_salary, "tag", "salary","location", location));
			obj.put("edu_salary",new Hbase2JSON().getFilter2O_AND(edu_salary, "edu", "salary", "location","tag", location,param));
			obj.put("tag",new Hbase2JSON().getFilter2A(tag, "tag", "count", "location", location));
			obj.put("edu",new Hbase2JSON().getFilter2A_AND(edu, "edu", "count", "location", "tag", location,param));
			out.println(obj);
			break;
		case "edu":
			obj.put("tag_salary",new Hbase2JSON().getFilter2O(tag_salary, "tag", "salary","location", location));
			obj.put("edu_salary",new Hbase2JSON().getFilter2O_AND(edu_salary, "tag", "salary", "location","edu", location, param));
			obj.put("tag",new Hbase2JSON().getFilter2A(tag, "tag", "count", "location", location));
			obj.put("edu",new Hbase2JSON().getFilter2A_AND(edu, "tag", "count", "location", "edu", location,param));
			out.println(obj);
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
