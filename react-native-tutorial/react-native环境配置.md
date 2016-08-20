#React Native Mac OS环境搭建步骤
在使用react native做项目开发之前，首先得搭建开发环境。这也是开发前所做的必要准备，下面就react native 环境搭建做了简单的介绍。
##Mac下安装
### 第一步：安装homebrew
[homebrew](http://brew.sh/)Mac系统的包管理器，用于安装NodeJS和一些其他必需的工具软件。将下一段复制到terminal中执行。
>/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

译注：在Max OS X 10.11（El Capitan)版本中，homebrew在安装软件时可能会碰到/usr/local目录不可写的权限问题。可以使用下面的命令修复

>sudo chown -R `whoami` /usr/local

### 第二步：安装Node
前面已经成功安装了[homebrew](http://brew.sh/),接下来通过homebrew来安装node.打来终端输入下面的命令。
>brew install node

node是react native的运行环境。
可以使用nvm来管理node的版本，但这个是非必须的。读者自行选择安装。[nvm安装教程地址](https://github.com/Cocoon-break/programmer-skills.git)

###第三步：安装React Native的命令行工具（react-native-cli）
用弄得安装cli，在终端输入下面的命令：
>npm install -g react-native-cli

安装cli之后可以进行react native项目初始化和项目升级。

译注：在输入上面的命令之后，如果你看到EACCES: permission denied这样的权限报错，那么请参照上文的homebrew译注，修复/usr/local目录的所有权：
>sudo chown -R `whoami` /usr/local

###第四步：推荐安装工具（可跳过）

####Watchman
[Whatchman](https://facebook.github.io/watchman/docs/install.html)是由Facebook提供的监视文件系统变更的工具。安装此工具可以提高开发时的性能（packager可以快速捕捉文件的变化从而实现实时刷新）。
在终端输入下面的命令
>brew install watchman

**注：** 在react native早期的版本中如果没有安装watchman,可能会出现一个node.js监视文件系统的bug

####Flow
[Flow](https://www.flowtype.org/)是一个静态的JS类型检查工具。译注：你在很多示例中看到的奇奇怪怪的冒号问号，以及方法参数中像类型一样的写法，都是属于这个flow工具的语法。这一语法并不属于ES标准，只是Facebook自家的代码规范。所以新手可以直接跳过（即不需要安装这一工具，也不建议去费力学习flow相关语法）。在终端输入下面的命令
>brew install flow

###第五步：代码编辑工具
个人推荐使用IntelliJ IDEA。当然也有其他的开发工具，如：sublime，atom等。这里使用IDEA，是为了开发Android方便。不用安装两个开发工具。IDEA 有两个版本，一个是收费的一个免费的。只有收费版本的才能支持jsx语法。

直接通过官网下载 **IDEA Ultimate** 版，安装之后输入
`http://idea.qinxi1992.cn`进行破解。

[IntelliJ IDEA下载地址](https://www.jetbrains.com/idea/)

###ios开发工具
React Native目前需要Xcode 7.0 或更高版本。建议通过app store 下载最新版本。

>虽然一般来说命令行工具都是默认安装了，但你最好还是启动Xcode，并在Xcode | Preferences | Locations菜单中检查一下是否装有某个版本的Command Line Tools。Xcode的命令行工具中也包含一些必须的工具，比如git等。

###android环境
####jdk安装
进入oracle官网下载最新的jdk [jdk下载地址](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)

####Android sdk安装

- 在terminal下输入下面命令进行android-sdk管理工具

	```brew install android-sdk```
	
- 配置java和android环境变量。在 **~/.bash_profile**文件中通过vim 进行编辑添加以下代码。（**注：**在用户目录下没有这个文件，通过 **cat .bash_profile**创建文件）

	```
	export ANDROID_HOME=/usr/local/opt/android-sdk
	export JAVA_HOME=$(/usr/libexec/java_home)
	```

- 安装完成之后再terminal里输入`android`进行Android各个版本sdk的下载管理。下载开发所需开发版本即可。

- 由于天朝的原因，我们访问不了谷歌。所以我们需要翻墙进行下载android sdk。[翻墙教程](https://github.com/Cocoon-break/programmer-skills/blob/master/over-the-wall-tutorial.md)。当然我们也可以通过国内镜像进行下载。[镜像下载教程](http://android-mirror.bugly.qq.com:8080/include/usage.html)


###第六步：Intelli IDEA配置sdk

![Macdown ScreenShot](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native-tutorial/screenShot/idea-1.png)

![Macdown ScreenShot](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native-tutorial/screenShot/idea-1.png)
###第七步：GenyMotion模拟器安装
GenyMotion是Android模拟器，企业版是收费的，个人开发版是免费的。

安装GenyMotion是需要安装virtualBox,在安装GenyMotion的时候会提示需要哪个版本的virtualBox。具体去下载即可。使用个人版的时候需要去他们官网注册账号。

**注：**记住账号密码，在下载对应版本的模拟器的时候需要账号密码。

从这里[GenyMotion](http://genymotion.en.softonic.com/)下载android模拟器，下载完成之后选择需要的机型即可。

###第八步：初始化工程

***打开ios工程***

选择一个目录cd 到改目录下初始化一个react native工程AwesomeProject

>react-native init AwesomeProject

>cd AwesomeProject/ios

>open .

双击AwesomeProject.xcodeproj，会使用xcode打开工程，点击run按钮。ios工程运行成功。

***打开android工程***

通过idea打开AwesomeProject目录下的**android工程**，点击run按钮，选择模拟器运行工程。

***编辑代码***

用idea打开AwesomeProject目录，对index.ios.js或者index.android.js。进行编辑。这时idea会提示是否使用jsx语法。

	index.ios.js是ios工程入口，index.android.js是Android工程入口





