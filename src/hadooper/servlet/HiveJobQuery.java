package hadooper.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HiveJobQuerry
 */
@WebServlet("/HiveJobQuery")
public class HiveJobQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private Statement st;
	private ResultSet rs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HiveJobQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//编码设置，所有数据初始化
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();
			session.removeAttribute("list");
			//获取表单数据
			/**/String location = request.getParameter("job_location");
			/**/String edu = request.getParameter("edu");
			String exp = request.getParameter("exp");
			/**/String job = request.getParameter("job");
			/**/String tecn = request.getParameter("tecn");
			/**/String plang = request.getParameter("plang");
			String industry = request.getParameter("industry");
			String people = request.getParameter("people");
			String financing = request.getParameter("financing");
			/**/String salaryh = request.getParameter("salaryh");
			/**/String salaryl = request.getParameter("salaryl");
		/* 
		 * flag 查询成功的标志
		 * first 第一个where条件标志
		 * */
			boolean flag = false;
			boolean first = true;
			String info = "";
			String sql = "select * from jobtable where ";
			String limit = " limit 200";
			String[] tecns = tecn.split(";");
			String[] plangs = plang.split(";");
			if(location != null) {
				if(first) {
					sql += "company_location = '"+location+"'";
					first = false;
				}
				else
					sql += " and company_location = '"+location+"'";
			} 
			if(edu != null) {
				if(first) {
					sql += "job_edu_require rlike '"+edu+"'";
					first = false;
				}
				else
					sql += " and job_edu_require rlike '"+edu+"'";
			}
			if (job != null) {
				if(first) {
					sql += "job_tag='"+job+"'";
					first = false;
				}
				else
				sql += " and job_tag='"+job+"'";
			} 
			if (!salaryl.isEmpty()) {
				if(first) {
					sql += "job_salaryl>"+salaryl;
					first = false;
				}
				else
				sql += " and job_salaryl>"+salaryl;
			}
			if (!salaryh.isEmpty()) {
				if(first) {
					sql += "job_salaryh<"+salaryh;
					first = false;
				}
				else
				sql += " and job_salaryh<"+salaryh;
			}
			if (!tecn.isEmpty()) {
				if(tecns.length > 2) {
				for(String s :tecns) {
					info += (s + "|");
					}
				}else{
					info += (tecn + "|");
				}
			}
			if (!plang.isEmpty()) {
				if(plangs.length > 2){
					for(String s :plangs) {
						info += (s + "|");
					}	
				}else{
					info += plang ;
				}
			}
			if(!info.equals("")) {
				if(first) {
					sql += "job_info rlike '"+info+"'";
					first = false;
				}
				else
				sql +=  " and job_info rlike '"+info+"'";
			}
			
			if(exp != null) {
				if(first) {
				sql += "job_exp_require rlike '"+exp+"'";
				first = false;
			}
			else
				sql += " and job_exp_require rlike '"+exp+"'";
			} 
			if (industry != null) {
				if(first) {
					sql += "company_industry='"+industry+"'";
					first = false;
				}
				else
				sql += " and company_industry='"+industry+"'";
			}
			if (people != null) {
				if(first) {
					sql += "company_people='"+people+"'";
					first = false;
				}
				else
				sql += " and company_people='"+people+"'";
			}
			if (financing != null)	{
				if(first) {
					sql += "company_financing_stage='"+financing+"'";
					first = false;
				}
				else
				sql += " and company_financing_stage='"+financing+"'";
			}
			sql += limit;
			System.out.println(sql);
			try {
			con = Jdbc2HiveUtil.getConn();
			st = con.createStatement();
			rs = st.getResultSet();
			List<String[]> list = new ArrayList<String[]>();
			rs = st.executeQuery(sql);
			
			while (rs.next()) {
				flag = true;
				list.add(new String[] {
						rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
						rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(16),rs.getString(17)
				});
			}
			session.setAttribute("list", list);
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			if(flag) {
				response.sendRedirect("recommend.jsp?current=0&current_page=1");
			}else {
				//错误页面跳转
				response.sendRedirect("NotFound.html");
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
