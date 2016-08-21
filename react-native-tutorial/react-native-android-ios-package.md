#react native 环境下ios和android打包
##android 打包
* 生成签名文件keystore
* 使用命令生成签名文件
	  1. keytool -genkey -v -keystore xxx.keystore -alias xxx-alias -keyalg RSA -keysize 2048 -validity 10000
	  
	  **注:** xxx.keystore 是生成的文件名，xxx-alias是别名，后面有用 
	  
	  2. 按提示输入对应的信息,xxx代表自己填写。            
        
    	```密码：xxx
		名字与姓氏：xxx
		组织单位名称：xxx
		组织名称：xxx
		城市名称：xxx
		地区代码：cn
		```
* 将生成的签名文件xxx.keystore复制到react native 工程下android/keystores 下。
* 在android/app/build.gradle 下进行代码配置。

		signingConfigs {
        release {
            storeFile file("../keystores/xxx.keystore")
            storePassword "xxx"
            keyAlias "xxx-alias"
            keyPassword "xxx"
        }
    	}
    	buildTypes {
        release {
            minifyEnabled enableProguardInReleaseBuilds
            proguardFiles getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            signingConfig signingConfigs.release
        }
    	}
* 在终端cd 到工程中的android目录，执行./gradlew assembleRelease进行编译打包。
* 编译之后cd app/build/output/apk,使用open . 打开finder，可以看到该目录下有几个.apk文件。其中app-release.apk是签名之后的发布版本。提交到各个android应用市场使用这个即可。

**注：**一些市场提交之前是需要进行加密的，例如360应用市场，但是加密之后，签名会失效，这个使用需要我们进行重签名。

* 使用命令对Android apk进行签名：
	1. 将数字证书和为签名的apk放置在同一个文件夹中
	2. jarsigner -verbose -keystore xxx.keystore -signedjar signed_xxx_v0.1.0.apk xxx_v0.1.0.apk xxx-alias 
	
	```
	-verbose 是为了打印签名过程中的log信息
	-keystore 是自己生成的签名文件hxg-bank-school.keystore
	-signedjar 有三个参数，第一个是签名之后的apk 第二个是签名之前的apk 第三个是你签名文件hxg-bank-school.keystore的别名 ```
	
android打包参考[react native官方文档](http://facebook.github.io/react-native/docs/signed-apk-android.html)

##ios打包
* 准备csr文件
	1. 在launchpad中查找钥匙串访问，如果没有的话在launchpad的搜索中输入key,就能够找到。
	![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-ad0808d89178b859.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)   
	
	2. 从工具栏里面选择**钥匙串访问->证书助理->从证书颁发机构请求证书**
	![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-f30c1698a6bc79e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
	3. 提示输入邮件地址，选择存储到磁盘, 在电脑上就会生成一个文件。
	![MacDown ScreentShot](http://upload-images.jianshu.io/upload_images/1274138-24facb0e48b85b4a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
* 准备签名证书。登录苹果开发者中心 [苹果开发者中心](https://developer.apple.com)这里的前提是你拥有开发者帐号，并且已经付费了。
	1. 点击进入**Certificates, Identifiers & Profiles**(专门生成证书，绑定Bundle Id，绑定device设备，生成描述文件的地方)

	2. 选择添加证书
		
		![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-6e167250ad616b18.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
	3. 选择证书类型，开发证书只能是在开发上使用也就是说开发证书签名生成的ipa文件是不能上传到appstore上的。只能是内部测试使用。
	![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-1b6916e4fc4b352c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	4. 选择continue,continue到第三步的时候提示上传csr文件，这个文件就是我们上一步生成的那个文件，上传即可。
	
	5. 添加appId和Bundle Identifier.这里的Bundle ID必须和工程中的Bundle ID一致。一致continue直到Register 和Done.App ID创建完成。
	
		![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-8510374b1d2e269d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
	6. 生成描述文件
	
	   ![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-34d1fc1c18b8a951.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
	7. 选择appID,这里的appID是 **第四步和第五步** 生成的.
	
	  ![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-8d669ba0500397a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	
	8. 选择发布证书, 这里的发布证书是**第2，3，4步**生成的
	  ![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-03caf3bb128101f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	  
	9. 填写描述文件的名称
	
	 ![MacDown ScreenShot](http://upload-images.jianshu.io/upload_images/1274138-5038e40b6bb04861.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
	 
	10. 生成推送证书，点击App IDs,选择其中的一个app id,然后点击Edit,找到Push Notifications选项，上传csr文件，生成推送证书。
	
	11. 将生成的证书文件，描述文件，推送证书下载双击安装，然后在ios工程中的Build Setting 中的Code Signing选择刚才安装的证书，在Capabilities打开Push Notifications开关。
	
* 打包发布到蒲公英
	1. 在Build Setting 中选择Code Siging选择发布证书。
	2. 在运行的选择模拟器的地方改成Generic IOS Device
	3. 在mac顶栏中选择Product->Archive之后等待xcode完成操作，之后在有边侧栏选择Export,选Save for Ad Hoc Deployment,next.选择开发团队Beijing Huaxia Wisdow Education Technology Institue Co.,Ltd.接下来一直next,直到打包完成。
	4. 将生成的ipa安装包上传到蒲公英上
	
	![ios pagekage](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native-tutorial/screenShot/ios-package1.png)
	
	
IOS打包参考：[iOS App上架流程2016](http://www.jianshu.com/p/b1b77d804254)