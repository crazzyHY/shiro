<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/2
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>login</h2>
<form action="/doLogin" method="post">
    用户名：<input type="text" name="username" ><br/>
    密码：<input type="password" name="password"><br/>
    <%--自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>--%>
    <input type="submit" value="登录">

    ${error}
</form>
</body>
</html>
