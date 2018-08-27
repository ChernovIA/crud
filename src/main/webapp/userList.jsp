<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 09.08.2018
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="users" class="java.util.ArrayList" scope="session"/>
<html>
<head>
    <title>Users list</title>
</head>
<body>
<table border="0" cellpadding="1" cellspacing="1" style="width:500px">

    <table align="center" border="1" cellpadding="1" cellspacing="1" style="width:500px">
        <caption><p>Users list</p> <p><a href="AddUser">Add User</a></p></caption>
        <tbody>
        <tr>
            <td style="text-align:center"><b>Login</b></td>
            <td style="text-align:center"><b>Name</b></td>
            <th colspan=2><b>Action</b></th>
        </tr>


        <c:forEach items="${users}" var="usr">
            <tr><td style="text-align:center">${usr.login}</td>
                <td style="text-align:center">${usr.name}</td>
                <td style="text-align:center"><a href="EditUser?id=<c:out value="${usr.id}"/>">Update</a></td>
                <td style="text-align:center"><a href="DeleteUser?&id=<c:out value="${usr.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <th style="text-align:center" colspan=4><a href="DefaultSettings">default</a></th>
        </tr>
        </tbody>
    </table>

</table>

</body>
</html>
