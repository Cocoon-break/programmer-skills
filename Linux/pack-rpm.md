[TOC]

# Linux下打RPM包

## 工具准备

1. 安装rpmtools工具

   ```sh
   yum install rpmdevtools
   ```

2. 创建文件夹

   ```sh
   mkdir -pv ~/rpmbuild/{BUILD,BUILDROOT,RPMS,SOURCES,SPECS,SRPMS}
   ```



## 配置文件及源码准备

1.  spec脚本文件创建，在SPECS文件夹下创建一个.spec文件 如h5-megvii.spec

   ```sh
   Name:           h5-megvii
   License:        copyright
   Version:        1.0
   Release:        1%{?dist}
   Summary:        HTML FaceID Server
   AutoReqProv:    no

   Group:          Megvii
   URL:            http://www.megvii.com
   Source:         %{name}-%{version}.tar.gz
   BuildRoot:      %{_tmppath}/%{name}-%{version}-%{release}-root

   %description
   The rpm version of html-megvii package

   %prep
   %setup -q

   %build
   %install
   rm -rf $RPM_BUILD_ROOT
   mkdir -p $RPM_BUILD_ROOT
   cp -a * $RPM_BUILD_ROOT

   %clean
   rm -rf $RPM_BUILD_ROOT

   %files
   %defattr(-,root,root,-)
   /opt/megvii

   ```

   spec脚本中各个配置项说明

   ```sh
   Name: 软件包的名称，后面可使用%{name}的方式引用
   License: 软件授权方式
   Version: 软件的实际版本号,后面可使用%{version}引用
   Release: 发布序列号,标明第几次打包，后面可使用%{release}引用
   Summary: 软件包的内容概要
   AutoReqProv: 此域用于指示RPM是否自动查找软件所需的共享库与其提供的共享库

   Group: 软件分组
   URL: 软件的主页
   Source: 源代码包，可以带多个用Source1、Source2等源，后面也可以用%{source1}、%{source2}引用
   BuildRoot: 这个是安装或编译时使用的“虚拟目录”,一般定义为:%{_tmppath}/%{name}-%{version}-%{release}-root

   %description 软件的详细说明

   %prep 预处理脚本
   %setup 把源码包解压并放好,将SOURCES下的tar.gz 文件解压至BUILD文件夹下

   %build 开始构建包
   %install 开始把软件安装到虚拟的根目录中

   %clean 清理临时文件

   %files 定义那些文件或目录会放入rpm中,files下的文件，必须是tar包解压开存在的文件
   %defattr (-,root,root) 指定包装文件的属性，分别是(mode,owner,group)，-表示默认值，对文本文件是0644，可执行文件是0755
   ```

   **注1:**	SOURCES下的tar.gz包的文件名必须和SPECS下 xxx.specs  文件中的Source 保持一致。

   **注2:**	SOURCES下的tar.gz包**解压开的文件夹**名称也应为SPECS下 xxx.specs  文件中的Source 对应的名称(无须tar.gz)。

   **注3:**	%files标签下的文件路径必须存在，如/opt/megvii ,在tar.gz 解压开的文件路径是存在的

2. 添加源码文件

   ```sh
   tar czvf h5-megvii-1.0.tar.gz h5-megvii-1.0
   cp h5-megvii-1.0.tar.gz rpmbuild/SOURCES/
   ```

## 执行打包命令

```sh
rpmbuild --nodeps -ba rpmbuild/SPECS/h5-megvii.spec
```

打完包之后的rpm包文件位置在`rpmbuild/RPMS`文件夹中