#react native android 和 iOS启动白屏
### android白屏解决办法
- 创建一个style样式，在android/app/src/res/values下的styles文件中添加样式代码,代码中颜色为app的主色调。

```
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme"
           parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">#4de5c0</item>
        <item name="colorPrimaryDark">#4de5c0</item>
        <item name="colorAccent">#4de5c0</item>
        <!--<item name="actionSheetStyle">@style/ActionSheetStyleiOS7</item>-->
    </style>

    <style name="Theme.Start" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="android:windowBackground">@mipmap/splash</item>
        <item name="colorPrimaryDark">#4de5c0</item>
    </style>
</resources>
```

- 在Androidmanifest.xml中启动activity中添加,android:theme="@style/Theme.Start",这里的Theme.Start就是我们在配置文件中写的，这里的MainActivity是react native 中加载jsbundle的
	
	```
		<activity
                android:name=".MainActivity"
                android:configChanges="keyboard|keyboardHidden|orientation|screenSize"
                android:label="@string/app_name"
                android:screenOrientation="portrait"
                android:theme="@style/Theme.Start">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
	```
	
###ios 启动白屏解决办法
- 在AppDelegate.m中找到RCTRootView 初始化代码，在RCTRootView初始化之后添加如下代码
	
	```
	NSArray *allPngImageNames = [[NSBundle mainBundle] pathsForResourcesOfType:@"png" inDirectory:nil];
  for (NSString *imgName in allPngImageNames){
    if ([imgName containsString:@"LaunchImage"]){
      UIImage *img = [UIImage imageNamed:imgName];
      
      if (img.scale == [UIScreen mainScreen].scale && CGSizeEqualToSize(img.size, [UIScreen mainScreen].bounds.size)) {
        rootView.backgroundColor = [UIColor colorWithPatternImage:img];
      }
    }
  }
	```
