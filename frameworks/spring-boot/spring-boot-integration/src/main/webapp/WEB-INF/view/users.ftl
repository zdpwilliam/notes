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
        #foreach($user in $users)
        #end
            <tr>
                <td>$user.id</td>
                <td>$user.username</td>
                <td>$user.password</td>
                <td><a href="/updateUser/$user.id">修改</a></td>
            </tr>
    </tbody>
</table>
</body>
</html>