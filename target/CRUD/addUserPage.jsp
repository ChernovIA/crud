<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 08.08.2018
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add/edit user</title>
</head>
<body>

    <form style="align-content: center" action="AddUser" method="POST">

        <p>Login: <input name="login" type="text" /></p>
        <p>Password: <input name="password" type="password" /></p>
        <p>Name: <input name="name" type="text" /></p>
        <p><input type="submit" value="add user" /></p>

    </form>

</body>
</html>
