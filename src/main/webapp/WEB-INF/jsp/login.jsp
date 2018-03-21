<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="../jq/jquery.min.js"></script>
    <title>Title</title>
</head>
<body>
<h2>login</h2>
<form action="/doLogin" method="post">
    用户名：<input id="username" type="text" name="username"><br/>
    密码：<input id="password" type="password" name="password"><br/>
    自动登录：<input type="checkbox" name="rememberMe" value="true"><br/>
    <%--<c:if test="${!captchaEbabled}">--%>
        <div class="login_code">
            <ul>
                <li>
                    <label>验证码：</label>
                </li>
                <li class="input_code">
                    <input id="code" name="captchaCode" size="6"/>
                </li>

                <li>
                    <span> <img class="rcCaptcha-btn rcCaptcha-img" src="${baseURL}/rcCaptcha.jpg"
                                alt="点击更换验证码" title="点击更换验证码" width="65" height="24"/>
                    </span>
                </li>
            </ul>
        </div>
    <%--</c:if>--%>
    <input type="submit" value="登录">

    ${error}
</form>
<script>

</script>
</body>
</html>
