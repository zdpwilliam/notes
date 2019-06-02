## Ubuntu18.04 搭建 shadowsocks 代理服务器

> HTTP/HTTPS 代理服务器是拿来转发 HTTP/HTTPS 协议的请求的
> “Shadowsocks” 只能代理转发 HTTP/HTTPS 协议的请求。如果你要和一台国外主机建立 TCP 连接，抱歉不能做到，只能使用 VPN 技术来实现。

### 1. 安装 pip3

`sudo apt-get install python3-pip`

PS：python3 的版本，Linux/Unix 下不加3 默认是 python2（python2在2020后官网不再维护）

### 2. 使用 pip3 安装 shadowsocks[pip 默认是 python2 的]

安装 shadowsocks 软件需要的 python3-setuptools 库:

```
sudo apt-get install python3-setuptools

pip3 install shadowsocks 或 pip3 install https://github.com/shadowsocks/shadowsocks/archive/master.zip
```

PS：缺少python包依赖造成的问题

安装完成后校验：`ssserver --version`

### 3. 配置 shadowsocks 服务

- 创建 shadowsocks.json 配置文件

`sudo vim /etc/shadowsocks.json`

- 编辑 shadowsocks.json

```
{
	"server":"服务器接收并处理的公网IP",
    "server_port":1080, 		// 对外提供服务的端口，注意防火墙开放该端口
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"password",    // 客户端连接需要的密码
    "timeout":600,
    "method":"aes-256-cfb"    // 通信过程使用的加密算法
}
```

PS：注意去掉后面的注释

### 4. 启动 shadowsocks

`sudo ssserver -c /etc/shadowsocks.json -d start`

### 5. 进阶配置[使用 systemctl 管理、并实现开机自启]

- 创建 shadowsocks.service文件 `vim /lib/systemd/system/shadowsocks.service`

- 复制粘贴以下内容后，保存退出

	```
	[Unit]
	Description=Shadowsocks Server
	After=network.target
	 
	[Service]
	ExecStart=/usr/local/bin/ssserver -c /etc/shadowsocks.json
	Restart=on-abort
	 
	[Install]
	WantedBy=multi-user.target
	```

- 使用 systemctl 和 service 进行管理：启动、查看状态、停止 shadowsocks 服务[ 下面的两种方式都可以, ".service" 可以省略]
	
	```
	systemctl [start、status、stop] shadowsocks.service
	service shadowsocks.service [start、status、stop]
	```
 
- 开机自启【开启、关闭】

	```systemctl [enable、disable] shadowsocks.service```

### 6. 客户端安装和配置使用 shadowsocks

	`MacOS Linux Windows Android 等客户端下载：https://github.com/shadowsocks`
