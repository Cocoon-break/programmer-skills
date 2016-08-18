#git简易教程

##windows下安装git

- 下载git安装包，选择windows版本。[下载地址](http://git-scm.com/downloads)

- 使用默认设置即可，一路next,直到git 安装完毕

- 找到git安装路径中的bin的位置。如 D:\applicationUtil\Git\bin

- 找到git安装路径中git-core的位置。如 D:\applicationUtil\Git\git-core

- 添加环境变量。跟配置JAVA_HOME一样把刚才的三四步中的路径添加到环境变量
	
	- 计算机->属性->高级系统设置->环境变量->系统变量中的path.把路径复制进去即可。

- 在cmd下运行`git --version` 显示版本信息。表示git 安装完成。

## mac下安装git

- 如果安装了xcode就不需要单独安装git了,xcode 已经将git安装了。

- 安装Homebrew,建议在mac下用brew来管理开发工具。把下面的命令复制到terminal执行
	
	`/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`

- 安装git. 执行命令 `brew install git`
- 检查安装是否完成。`git --version`

## git 学习地址

[**廖雪峰的git教程**](http://www.liaoxuefeng.com/wiki/0013739516305929606dd18361248578c67b8067c8c017b000)
