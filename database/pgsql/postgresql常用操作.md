## postgresql常用操作

### 1. postgresql 安装 [Ubuntu18.04]

- 通过 Ubuntu 的默认存储库安装 [deb官网配置](https://www.postgresql.org/download/linux/ubuntu/)

	```
	deb http://apt.postgresql.org/pub/repos/apt/ bionic-pgdg main
	wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
sudo apt-get update
	sudo apt install postgresql-9.6[或其它版本号]
	```

- 或通过源码安装 [postgresql-9.6版本教程](https://www.postgresql.org/docs/9.6/installation.html)

- 设置允许远程访问
	
	修改 /etc/postgresql/9.x/main/ 目录下的两个配置文件：
	postgresql.conf 中的 `listen_addresses = '*'` 和
	pg_hba.conf 末尾追加 `host all all 0.0.0.0/0 md5`
	
	PS: `sudo systemctl restart postgresql`
	
### 2. postgresql 的使用

- 默认情况下，Postgres 使用称为“角色”的概念来处理身份验证和授权。类似于普通的Unix风格的账户，但是 postgres 并没有区分用户和组，而是倾向于更灵活的术语“角色”。安装后，postgres 被设置为使用ident身份验证，这意味着它将 postgres 角色与匹配的 unix/linux 系统帐户相关联。 若 postgres 中存在一个角色，则具有相同名称的 unix / Linux用户名可作为该角色登录。

- 有几种方式可以使用此帐户访问Postgres：

	```
	sudo -i -u postgres					//切换到 Postgres 帐户
	sudo -u postgres psql[other cmd]	//不切换帐户的情况下访问 Postgres
	```
- 创建新的角色 william [以progres或其它有相关权限的角色]

	```
	createuser --interactive [以交互方式] //删除用 dropuser
	passwd william						//修改系统用户密码
	```
	以新的角色打开 postgres 提示要使用基于身份验证的 ident 验证进行登录，您需要一名与 postgres 角色和数据库名称相同的 linux 用户：

	```
	sudo adduser william
	sudo -i -u william psql
	sudo -u william psql 				//或可以这样做内联
	
	psql -d postgres					//用你的用户连接到不同的数据库
	\password william					//修改系统用户密码
	\conninfo							//检查当前的连接信息
	```
- 远程控制台登录

	```
	psql -U user -W password -h host -p port -d db
	
	```
- psql 是 PostgreSQL 的交互式客户端工具，具体操作参考：`psql --help`


### 3. 客户端常用操作指令

	\h		#查看所有的sql关键字
	\? 		#命令行操作的帮助
	\d 		#查看当前schema 中所有的表
	\q		#退出pg命令行
	\d		#schema.table 查看表的结构
	\x		#横纵显示切换 
	\dT+	#显示扩展类型相关属性及描述
	\dx		#显示已安装的扩展插件
	\l		#列出所有的数据库
	\! hostname			#列出当前的主机名
	\timing				#显示执行时间
	\c database_name	#切换数据库
	set search_path to schemaA, schemaB	#切换schema
	explain analyze sql					#解释或分析sql执行过程
	

