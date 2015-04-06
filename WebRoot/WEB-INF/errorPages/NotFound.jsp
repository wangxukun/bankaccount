<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>404: 页面未找到</title>
  </head>
  
  <body>
    <h2>出错!</h2>
    对不起, 在此站点不能找到匹配
 <%--    ${pageContext.request.getAttribute("javax.servlet.forward.request_uri")} 用下面的替代--%>
 <%= request.getAttribute("javax.servlet.forward.request_uri") %>
    的页面。 你可以做以下操作:
    <ul>
    <li>到此站点 <a href="/" target="_top">首页</a>
    <li>搜索相关页面<br />
	    <form action="https://www.baidu.com/s">
		    关键字:<input type="text" name="wd" /><input type="submit" value="搜索" />
	    </form>
    </ul>
  </body>
</html>
