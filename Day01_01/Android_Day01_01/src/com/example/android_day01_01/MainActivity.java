package com.example.android_day01_01;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	/**
	 *1.android 环境的搭建
	 * 	1.1 windows环境下搭建 (为了方便搭建环境，统一让学生装32为开发环境，)
	 * 		开发工具：方案一：
	 * 				(1)adt-bundle-windows-x86-20131030.zip(直接解压直接用)
	 * 				(2)jdk-7-windows-x86.exe
	 * 				(3)ADT-22.0.0.zip(*讲解如何配置eclipse的Android开发环境使用)
	 * 				(4)SDK集成封装(Android4.3)(*讲解如何配置eclipse的Android开发环境使用)
	 * 				方案二：
	 * 				Android Studio;不推荐讲课使用；可以跟学生介绍Android Studio
	 * 
	 * 
	 * 
	 * 		操作步骤：1.安装JDK
	 * 					(1)安装jdk-7-windows-x86.exe，注意选择路径（建议直接安装C盘，避免环境配置的时候出现问题);
	 * 					(2)**(也可以不配置环境变量)**配置环境变量：右击“我的电脑”-->"高级"-->"环境变量"
	 * 						1* 在系统变量里新建“JAVA_HOME”变量，变量值为：C:\Program Files\Java\jdk1.7.0_14（根据自己的安装路径填写）
	 * 						2* 新建“classpath”变量，变量值为：.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
	 * 						3* 在path变量（已存在不用新建）添加变量值：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin（注意变量值之间用“;”隔开）
	 * 						4* “开始”-->“运行”-->输入“javac”-->"Enter"，如果能正常打印用法说明配置成功！
	 * 
	 * 						(补充环境变量的解析:
	 *							JAVA_HOME:jdk的安装路径
	 *							classpath:java加载类路径，只有类在classpath中java命令才能识别，在路径前加了个"."表示当前路径。
	 *							path：系统在任何路径下都可以识别java,javac命令)
	 *				2.将adt-bundle-windows-x86-20131030.zip 解压
	 *					(1).找到eclipse文件夹找下的eclipse.exe 双击打开；
	 *						(1.1)注意选择工作目录(代码存放的位置，意见学生更改到页面路径，并引导学生创建（1000phone文件夹）)
	 *				3. 如何配置eclipse的Android开发环境使用
	 *						(1)安装android 开发插件
	 *							(1.1)打开Eclipse, 在菜单栏上选择 help->Install New SoftWare-->点击 Add按钮-->填写：Name:android(随意)
	 *									Location:ADT 路径(点击Archive..选择路径)/或则填写下载路径https://dl-ssl.google.com/android/eclipse/-->
	 *									点击OK -->点击“Next”—> “Next”—>“I accept the terms of „”接受协议。(注意选取下一步的时候不选中往届网络更新)
	 * 									再点击Finish ，Ecplise此时会从加入的地址中自动下载跟安装ADT,稍等一会,安装完毕后,提示需要重启Ecplise。点击"OK"重启Ecplise，到此ADT安装完成
	 * 						(2)配置AndroidSDK
	 * 							(2.1)重启完Ecplise后,点击菜单“Window”-->“Preference”,打开了配置窗口,在左边选中Android,在右边点击Browse选择Android SDK的路径,点击"OK",至此环境配置完毕。
	 
	 * 	1.2 Mac 环境下搭建	 
	 * 		开发工具：方案一：
	 * 				(1)adt-bundle-mac-x86_64-20131030.zip(直接解压直接用)
	 * 				(2)jdk
	 * 				(3)ADT-22.0.0.zip(*讲解如何配置eclipse的Android开发环境使用)
	 * 				(4)SDK集成封装(Android4.3)(*讲解如何配置eclipse的Android开发环境使用)
	 * 				方案二：
	 * 				Android Studio;不推荐讲课使用；可以跟学生介绍Android Studio
	 *		操作步骤：1.安装JDK
	 *					(1)找到电脑上APP Store  下载JDK(MAC 版本)
	 *					(2) 一般不用配置JDK环境(MAC内置JDK环境)，-->找到电脑“终端”-->输入“javac”-->"Enter"，如果能正常打印用法说明配置成功！
	 *				2.将adt-bundle-mac-x86_64-20131030.zip 解压
	 *					(2).找到eclipse文件夹找下的eclipse.exe 双击打开；
	 *						(2.1)注意选择工作目录(代码存放的位置，意见学生更改到页面路径，并引导学生创建（1000phone文件夹）)
	 *				3.如何配置eclipse的Android开发环境使用
	 *						(插件配置跟windows配置相同，)
	 *					(2)配置AndroidSDK
	 * 							(2.1)重启完Ecplise后,点击ADT --->偏好设置,打开了配置窗口,在左边选中Android,在右边点击Browse选择Android SDK的路径,点击"OK",至此环境配置完毕。
	 *
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
