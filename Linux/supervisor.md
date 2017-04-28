#supervisor linux后台进程管理利器
supervisor 是基于python的进程管理工具

##install online
- ubuntu 系统安装 apt-get install supervisor
- 也可以通过pip install supervisor 但是需要手动启动
##install offline
- 下载安装包和依赖包详见官网 [https://pypi.python.org/pypi/supervisor] 3.3.1版本

- 解压安装tar.gz  然后cd 到目录中执行 python setup.py install

- 创建配置文件

-  执行`echo_supervisord_conf > /etc/supervisor/supervisord.conf `创建配置文件

-  创建supervisord 放在 init.d目录 下  **注：** prog_bin 为supervisors 可执行文件位置

   ```ssh
   #!/bin/sh
   #
   # /etc/rc.d/init.d/supervisord
   #
   # Supervisor is a client/server system that
   # allows its users to monitor and control a
   # number of processes on UNIX-like operating
   # systems.
   #
   # chkconfig: - 64 36
   # description: Supervisor Server
   # processname: supervisord

   # Source init functions
   . /etc/rc.d/init.d/functions

   prog="supervisord"

   prefix="/usr"
   exec_prefix="${prefix}"
   prog_bin="${exec_prefix}/bin/supervisord"
   PIDFILE="/var/run/$prog.pid"

   start()
   {
          echo -n $"Starting $prog: "
          touch $PIDFILE
          daemon $prog_bin -c /etc/supervisord.conf --pidfile $PIDFILE
          sleep 1
          [ -f $PIDFILE ] && success $"$prog startup" || failure $"$prog startup"
          echo
   }
   stop()
   {
          echo -n $"Shutting down $prog: "
          [ -f $PIDFILE ] && killproc $prog || success $"$prog shutdown"
          echo
   }

   case "$1" in

    start)
      start
    ;;

    stop)
      stop
    ;;

    status)
          status $prog
    ;;

    restart)
      stop
      start
    ;;

    *)
      echo "Usage: $0 {start|stop|restart|status}"
    ;;

   esac
   ```

-  修改指定要运行程序配置文件

   编辑supervisor.conf文件中的

   ```
   [include]
   files = /etc/supervisord.conf.d/*.conf
   ```

 ​

##how to use

在etc/supervisor.conf.d 目录下创建每个进程对应的配置文件 如：work.conf
具体配置
	directory= /opt/faceid/worker            //工程文件目录
	command= /opt/faceid/worker/start.sh  //启动命令
	autostart=true                                           //是否自启
	autorestart=true                                        //是否自动重启
	startretries= 10000                                  //重试时间
	startsecs=1                                                //启动时间
	stopasgroup=true
	killasgroup=true``
	stdout_logfile_maxbytes = 50MB
	stdout_logfile_backups = 10
	stdout_logfile = /var/log/megvii/%(program_name)s.log
##common command
- supervisorctl status 查看各个进程的状态
- supervisorctl reload 重启supervisor 下的所有进程
- supervisorctl stop name 停止指定进程
- supervisorctl start name 启动指定进程
- supervisorctl restart all  重启supervisor 下的所有进程



## FAQ

- Error: Another program is already listening on a port

  ```ssh
  find / -name supervisor.sock
  unlink /***/supervisor.sock
  ```

- 文件找不到

   `unix:///tmp/supervisor.sock no such file`

  编辑文件/etc/supervisord.conf  中的

  ```
  file = /tmp/supervisor.sock ;改成file = /var/run/supervisor.sock
  ```

​	然后执行

```ssh
touch /var/run/supervisor.sock
service supervisord restart
```

-  ERROR (spawn error)

  确认脚本文件能够正常使用

- error while loading shared libraries: libpython2.7.so.1.0

  找到libpython2.7.so.10点位置

  ```ssh
  [root@testlm01v ~]# locate libpython2.7.so.1.0
  /usr/local/Python-2.7.11/libpython2.7.so.1.0
  /usr/local/lib/libpython2.7.so.1.0
  ```

  把找到的位置复制到/etc/ld.so.conf.d/python.conf中如

  ```
  [root@testlm01v /etc/ld.so.conf.d]# cat python.conf 
  /usr/local/lib
  ```
