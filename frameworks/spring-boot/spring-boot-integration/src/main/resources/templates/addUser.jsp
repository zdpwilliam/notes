<%@ page import="com.william.model.User" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: william.zhang
  Date: 2016/3/18
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户列表</title>
</head>
<body>
  <a href="/addUser">添加</a>
  <table>
    <thead>
      <tr>
        <th>用户ID</th>
        <th>用户名</th>
        <th>用户密码</th>
        <th>修改</th>
      </tr>
    </thead>
    <tbody>
      <% List<User> users = (List<User>) request.getAttribute("users");
         for (User user : users) {
      %>
      <tr>
        <td><%=user.getId() %></td>
        <td><%=user.getUsername() %></td>
        <td><%=user.getPassword() %></td>
        <td><a href="/updateUser/<%=user.getId() %>">修改</a></td>
      </tr>
      <%
         }
      %>
    </tbody>
  </table>
</body>
</html>
