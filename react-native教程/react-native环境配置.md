#React Native Mac OS环境搭建步骤
在使用react native做项目开发之前，首先得搭建开发环境。这也是开发前所做的必要准备，下面就react native 环境搭建做了简单的介绍。
##安装
## 第一步：安装homebrew
[homebrew](http://brew.sh/)Mac系统的包管理器，用于安装NodeJS和一些其他必需的工具软件。
>/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

译注：在Max OS X 10.11（El Capitan)版本中，homebrew在安装软件时可能会碰到/usr/local目录不可写的权限问题。可以使用下面的命令修复

>sudo chown -R `whoami` /usr/local

## 第二步：安装Node
前面已经成功安装了[homebrew](http://brew.sh/),接下来通过homebrew来安装node.打来终端输入下面的命令。
>brew install node

##第三步：安装React Native的命令行工具（react-native-cli）
React Native的命令行工具用于执行创建、初始化、更新项目、运行打包服务（packager）等任务。打来终端输入下面的命令。
>npm install -g react-native-cli

译注：在输入上面的命令之后，如果你看到EACCES: permission denied这样的权限报错，那么请参照上文的homebrew译注，修复/usr/local目录的所有权：
>sudo chown -R `whoami` /usr/local

##推荐安装工具

##Whatman
[Whatchman](https://facebook.github.io/watchman/docs/install.html)是由Facebook提供的监视文件系统变更的工具。安装此工具可以提高开发时的性能（packager可以快速捕捉文件的变化从而实现实时刷新）。
在终端输入下面的命令
>brew install watchman

##Flow
[Flow](https://www.flowtype.org/)是一个静态的JS类型检查工具。译注：你在很多示例中看到的奇奇怪怪的冒号问号，以及方法参数中像类型一样的写法，都是属于这个flow工具的语法。这一语法并不属于ES标准，只是Facebook自家的代码规范。所以新手可以直接跳过（即不需要安装这一工具，也不建议去费力学习flow相关语法）。在终端输入下面的命令
>brew install flow

##代码编辑工具（推荐使用Intelli IDEA）
直接通过官网下载即可：[https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)

至此所有的react native支持工具已安装完成，接下来配置android和ios环境
##ios环境
React Native目前需要Xcode 7.0 或更高版本。你可以通过App Store或是到Apple开发者官网上下载。这一步骤会同时安装Xcode IDE和Xcode的命令行工具。
>虽然一般来说命令行工具都是默认安装了，但你最好还是启动Xcode，并在Xcode | Preferences | Locations菜单中检查一下是否装有某个版本的Command Line Tools。Xcode的命令行工具中也包含一些必须的工具，比如git等。

##android环境
###jdk安装
进入官网下载最新的jdk[http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)
###Android sdk使用镜像安装
具体请参考[sdk 安装](http://note.youdao.com/share/?id=0a481b0e70df80ba2b2888cb4280bf96&type=note#/)
>注意：打开sdk manager  下载android sdk的过程中会遇到无法下载的，这是由于我们下载的sdk是需要访问Google的 ，国内是无法访问的，这时候需要翻墙。从[这里](https://github.com/Cocoon-break/programmer-skills/tree/master/react-native%E6%95%99%E7%A8%8B/%E7%BF%BB%E5%A2%99host)下载host文件，替换本地的host的文件。然后通过sdk manager选择sdk下载。

##Intelli IDEA配置sdk

![idea-1](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native/screenShot/idea-1.png)

![idea-1](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native/screenShot/idea-2.png)
##GenyMotion模拟器安装
从这里[GenyMotion](http://genymotion.en.softonic.com/)下载android模拟器，下载完成之后选择需要的机型即可。

##测试安装
***ios环境测试***

选择一个目录cd 到改目录下初始化一个react native工程AwesomeProject
>react-native init AwesomeProject

>cd AwesomeProject/ios

>open .

通过xCode打开AwesomeProject.xcodeproj，点击run按钮

***android环境测试***

通过idea打开AwesomeProject目录下的android工程，点击run按钮，选择模拟器运行工程。




