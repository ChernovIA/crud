<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 08.08.2018
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>add/edit user</title>
</head>
<body>

    <form style="align-content: center" action="EditUser" method="POST">

        <p>ID: <input name="id" type="text" readonly="readonly" value="<c:out value="${user.id}" />" /></p>

        <p>Login: <input name="login" type="text" readonly = "readonly" value="<c:out value="${user.login}" />" /></p>
        <p>Password: <input name="password" type="password" value="<c:out value="${user.password}" />" /></p>
        <p>Name: <input name="name" type="text" value="<c:out value="${user.name}" />"  /></p>
        <p><input type="submit" value="save user"/></p>

    </form>

</body>
</html>
