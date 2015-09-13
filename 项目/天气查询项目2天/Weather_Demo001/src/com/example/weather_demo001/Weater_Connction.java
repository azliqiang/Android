package com.example.weather_demo001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Weater_Connction {
	/**
	 * 访问网络为了读取文件； 目的：链接网络获取String字符串信息，然后将数据解析展示到列表上
	 */
	public String DouwLoad(String path) {

		HttpURLConnection httpconn = null;// 定义URL请求
		InputStream inputs = null;// 输入流、字节流
		BufferedReader b = null;// 用来读取得到的字符串。嵌套在字节流外层
		try {
			StringBuffer sb = new StringBuffer();// 将得到的字符串信息缓冲到Stringbuffer中
			URL url = new URL(path);// 定义URL访问网络的路径
			httpconn = (HttpURLConnection) url.openConnection();// 通过HttpURLConnection
																// 打开网络链接
			httpconn.setReadTimeout(5 * 1000);// 设置访问网络的超时时间，为了读取数据
			httpconn.setConnectTimeout(5 * 1000);// 设置一个链接超时
			httpconn.setRequestMethod("GET");// 设置网络请求方式
			if (httpconn.getResponseCode() == 200) {// 访问网络的返回码
				inputs = httpconn.getInputStream();// 得到一个输入流的对象
				b = new BufferedReader(new InputStreamReader(inputs));// 将输入流信息转化为可以读取的BufferRead对象
				// 读取一行
				String line = null;// 暂时存放读取到得一行信息
				while ((line = b.readLine()) != null) {
					sb.append(line);// 将输入流的信息读取到StringBuffer
				}
			} else {// 上海
				httpconn.disconnect();// 断开网络链接
			}
			return sb.toString();// 返回String字符串
			/**
			 * 1.建立链接 2.将服务端的内容通过InputStream进行获取，缓冲到StringBuffer 。
			 * 3.然后将StringBuffer中拿出数据 ---String
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "TTTT";
		} finally {// 总执行
			httpconn.disconnect();// 关闭网络链接
			/**
			 * 抛出在网络的时候可能出现的异常状况
			 */
			try {
				inputs.close();
				b.close();// 关闭读取
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 关闭输入流
		}

	}

	// 判断手机是否联网
	public boolean IsNETworkHtttpConnection(Context context) {
		if (context != null) {
			// 哪一个服务项目//获取网络服务 ,获取访问系统是否联网的一个权限
			// 网络连接管理
			ConnectivityManager CManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = CManager.getActiveNetworkInfo();// 获取网络状态
			if (info != null) {
				return info.isAvailable();// 如果有网络true ,false
			} else {
				Toast.makeText(context, "网络没有连接", 1).show();
				return info.isAvailable();
			}
		}

		return false;
	}

}
