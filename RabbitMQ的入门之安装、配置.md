# RabbitMQ的入门之安装、配置

标签（空格分隔）： 未分类

---

###一、RabbitMQ简介
RabbitMQ是用Erlang实现的一个高并发高可靠AMQP消息队列服务器。

显然，RabbitMQ跟Erlang和AMQP有关。下面简单介绍一下Erlang和AMQP。

Erlang是一门动态类型的函数式编程语言，它也是一门解释型语言，由Erlang虚拟机解释执行。从语言模型上说，Erlang是基于Actor模型的实现。在Actor模型里面，万物皆Actor，每个Actor都封装着内部状态，Actor相互之间只能通过消息传递这一种方式来进行通信。对应到Erlang里，每个Actor对应着一个Erlang进程，进程之间通过消息传递进行通信。相比共享内存，进程间通过消息传递来通信带来的直接好处就是消除了直接的锁开销(不考虑Erlang虚拟机底层实现中的锁应用)。

AMQP(Advanced Message Queue Protocol)定义了一种消息系统规范。这个规范描述了在一个分布式的系统中各个子系统如何通过消息交互。而RabbitMQ则是AMQP的一种基于erlang的实现。AMQP将分布式系统中各个子系统隔离开来，子系统之间不再有依赖。子系统仅依赖于消息。子系统不关心消息的发送者，也不关心消息的接受者。


###二、RabbitMQ的安装与配置
* windows下的安装与配置  

RabbitMQ的运行需要erlang的支持，因此我们先安装erlang。  

32位下载地址：http://www.erlang.org/download/otp_win64_18.2.1.exe   

64位下载地址：http://www.erlang.org/download/otp_win32_18.2.1.exe   

双击选择默认安装就好。  


前面我们也讲到RabbitMQ就是一个服务器，下面我们就安装对应服务器。   

下载地址：http://www.rabbitmq.com/releases/rabbitmq-server/v3.3.4/rabbitmq-server-3.3.4.exe   

双击选择默认安装就好，安装好之后需要启动服务，cmd，进入到安装目录的sbin文件夹下，命令如下：  

    `cd C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.3.4\sbin`  
    
让后按照默认配置启动：  

    `rabbitmq-server start`  
    
RabbitMQ支持一些管理工具，例如web页面。利用如下命令在windows中开启RabbitMQ的管理插件：  

    `C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.2.3\sbin\rabbitmq-plugins.bat" enable rabbitmq_management`  
    
然后需要重启RabbitMQ的服务：  

    `net stop RabbitMQ && net start RabbitMQ`  
    
访问以下地址可以进入RabbitMQ的web管理界面：  

    http://localhost:15672  
    
默认的用户名和密码都是guest。  

    
检查RabbitMQ是否已经启动：  

    `netstat -ano|findstr 5672`  
    
此时可以看到RabbitMQ的默认端口5672的进程号，再利用任务管理器查看该进程号是否为RabbitMQ的进程，继而确定其是否已经启动。  

    
* linux下的安装与配置(以CentOS为例)

 1. 安装erlang  
 
    以root身份执行如下命令：  

    `su -c 'rpm -Uvh http://download.fedoraproject.org/pub/epel/6/i386/epel-release-6-8.noarch.rpm' `  
    
    `su -c 'yum install foo' `  

    `wget -O /etc/yum.repos.d/epel-erlang.repo http://repos.fedorapeople.org/repos/peter/erlang/epel-erlang.repo`  

    `yum install erlang`  

注意：第三个命令需要epel的最新版。  

 2. 安装Rabbitmq-server  
 
    `rpm --import http://www.rabbitmq.com/rabbitmq-signing-key-public.asc`    

    `yum install rabbitmq-server-3.2.3-1.noarch.rpm`(注：要下载下来)    
    
 3. 安装RabbitMQWeb管理插件  
 
    `rabbitmq-plugins enable rabbitmq_management`  

    `service rabbitmq-server restart`

与windows一样当安装了管理插件并且启动RabbitMQ服务之后就可以在浏览器查看web管理页面了，地址如下：  

    http://localhost:15672  
    
默认用户名和密码同样是guest  


注：当服务起不来的时候，使用netstat查看进程是否存在，如果存在用kill命令将其杀掉之后再启动就可以了。
  




