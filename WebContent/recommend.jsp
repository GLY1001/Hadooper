<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.util.Map.Entry" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href = "./css/recommend_style.css" type='text/css' rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/recommend.js"></script>
<link rel="stylesheet" href="css/index.css" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
</head>
<body class="bg">
	<!--头部-->
	<header class="header">
    	<h3>基于互联网大数据的招聘数据智能分析平台</h3>
    </header>
    <div class="nav">
    	<!--当前浏览位置-->
        <div class="location">
            <div class="site"><span></span>您目前所在的位置：><a href="recommend.jsp">相关招聘信息推荐</a></div>
        </div>
    	
    	<!-- 导航栏 -->
		<div class="nav_con"><ul>
			<li><a href="resume.html">求职意向表</a></li>
			<li><a href="other.html">深度分析</a><span></span></li>
			<li><a href="index.html">首页</a></li>
		</ul></div>
	</div>
	
	<div class="recommend_content">
    	<div class="recommend_nav">
    		<div class="title">
				
                <div class="title_right"><span></span>推荐职位</div>
                <div class="title_bottom"></div>
                                
    		<%
    		//获取session中的数据
    		List<String[]>  list= (List<String[]>)session.getAttribute("list");
    		/*
    		 * pageinfo 存储当前页面选择的公司的数据
    		 * index 当前页面公司列表的索引
    		 * count 统计公司列表
    		 * pageIndex 页数索引
    		 * pageCount 统计总页数
    		 * start 遍历的起点
    		 * end 遍历的终点
    		 * mean 数据长度与当页现实数据长度的结果
    		 * mod 数据长度与单页显示数据长度的俄模
    		 */
    		String[] pageinfo = null;
    		int index = 0;
    		int count = 0;
    		int pageIndex = 0;
    		int mean = (int)Math.round(list.size()/20.0);
    		int mod = list.size()%20;
    		int pageCount = (int)Math.round(list.size()/20.0);
    		int start = 0;
    		int end = 0;

    		pageCount = (mod<10 && mod!=0) ? pageCount+1 : pageCount; 
    		pageIndex = request.getParameter("current_page")!=null ? Integer.parseInt(request.getParameter("current_page")): 1;
    		start += (pageIndex-1)*20;
    		end = (pageIndex==pageCount) ? (mod==0 ? 20*pageIndex : ((start>end||start==end)? (start+mod) : mod)) : 20*pageIndex;
    		if (request.getParameter("current")!= null){
    			index = Integer.parseInt(request.getParameter("current"));
    		}
    		for (int i=start;i<end;i++){
    			String[] s = list.get(i);
    			String name =s[10];
    			 if (count == index){
 			    	pageinfo = s;
 			    	%>
	    		<div class="title_left"></div>
                <div class="current" onclick="go(<%=count %>,<%=pageIndex%>)"><span></span><%=name %></div>
                <div class="title_bottom"></div>
 			    	<%
 			    }else{
    			%>
               <div class="title_left"></div>
               <div class="title_right" onclick="go(<%=count %>,<%=pageIndex%>)"><span></span><%=name %></div>
               <div class="title_bottom"></div>
               <%}
  			 		count += 1;
               } %>
            </div>
        </div>
        <div class="recommend_container">
            <div class="recommend_show">
                <div id="main">
                	<div class="main_title_left"></div>
		            <div class="main_title_right" ><span></span>公司融资阶段</div>
		            <div class="main_div" id="financing">&emsp;&emsp;<%=pageinfo[0] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司产业类型</div>
		            <div class="main_div" id="industry">&emsp;&emsp;<%=pageinfo[1] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司地址</div>
		            <div class="main_div" id="location">&emsp;&emsp;<%=pageinfo[2] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司名称</div>
		            <div class="main_div" id="company_name">&emsp;&emsp;<%=pageinfo[3] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司性质</div>
		            <div class="main_div" id="nature">&emsp;&emsp;<%=pageinfo[4] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司概况</div>
		            <div class="main_div" id="overview">&emsp;&emsp;<%=pageinfo[5] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>公司人数</div>
		            <div class="main_div" id="people">&emsp;&emsp;<%=pageinfo[6] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位学历要求</div>
		            <div class="main_div" id="edu">&emsp;&emsp;<%=pageinfo[7] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位经验要求</div>
		            <div class="main_div" id="exp">&emsp;&emsp;<%=pageinfo[8] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位描述信息</div>
		            <div class="main_div" id="info">&emsp;&emsp;<%=pageinfo[9] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位名称</div>
		            <div class="main_div" id="job_name">&emsp;&emsp;<%=pageinfo[10] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位薪资</div>
		            <div class="main_div" id="salary">&emsp;&emsp;<%=pageinfo[11] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位索引标签</div>
		            <div class="main_div" id="job_tag">&emsp;&emsp;<%=pageinfo[12] %></div>
		            <div class="main_title_bottom"></div>
		            
		            <div class="main_title_left"></div>
		            <div class="main_title_right"><span></span>职位福利</div>
		            <div class="main_div" id="welfare">&emsp;&emsp;<%=pageinfo[13] %></div>
		            <div class="main_title_bottom"></div>
                	
				</div>
            </div>
        </div>
        <!--分页  -->
        <div class="page-icon">
        <% if(pageIndex==1){%>
		<span class="page-disabled"><i></i>上一页</span>
		<%}else{ %>
		<a class="page-next" href="recommend.jsp?current_page=<%=pageIndex-1 %>"><i></i>上一页</a>
		<%} %>
        <%
		for (int j=1;j<pageCount+1;j++){
			if(j==pageIndex){
		%>
		<span class="page-current"><a href="#"><%=j %></a></span>
		<%	}else{ %>
		<a href="recommend.jsp?current_page=<%=j %>"><%=j %></a>
		<%	}
		} %>
		<% if(pageIndex==pageCount){%>
		<span class="page-disabled">下一页<i></i></span>
		<%}else{ %>
		<a class="page-next" href="recommend.jsp?current_page=<%=pageIndex+1 %>">下一页<i></i></a>
		<%} %>
	</div>
    </div>
</body>
</html>