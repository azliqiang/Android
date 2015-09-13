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
	 * ��������Ϊ�˶�ȡ�ļ��� Ŀ�ģ����������ȡString�ַ�����Ϣ��Ȼ�����ݽ���չʾ���б���
	 */
	public String DouwLoad(String path) {

		HttpURLConnection httpconn = null;// ����URL����
		InputStream inputs = null;// ���������ֽ���
		BufferedReader b = null;// ������ȡ�õ����ַ�����Ƕ�����ֽ������
		try {
			StringBuffer sb = new StringBuffer();// ���õ����ַ�����Ϣ���嵽Stringbuffer��
			URL url = new URL(path);// ����URL���������·��
			httpconn = (HttpURLConnection) url.openConnection();// ͨ��HttpURLConnection
																// ����������
			httpconn.setReadTimeout(5 * 1000);// ���÷�������ĳ�ʱʱ�䣬Ϊ�˶�ȡ����
			httpconn.setConnectTimeout(5 * 1000);// ����һ�����ӳ�ʱ
			httpconn.setRequestMethod("GET");// ������������ʽ
			if (httpconn.getResponseCode() == 200) {// ��������ķ�����
				inputs = httpconn.getInputStream();// �õ�һ���������Ķ���
				b = new BufferedReader(new InputStreamReader(inputs));// ����������Ϣת��Ϊ���Զ�ȡ��BufferRead����
				// ��ȡһ��
				String line = null;// ��ʱ��Ŷ�ȡ����һ����Ϣ
				while ((line = b.readLine()) != null) {
					sb.append(line);// ������������Ϣ��ȡ��StringBuffer
				}
			} else {// �Ϻ�
				httpconn.disconnect();// �Ͽ���������
			}
			return sb.toString();// ����String�ַ���
			/**
			 * 1.�������� 2.������˵�����ͨ��InputStream���л�ȡ�����嵽StringBuffer ��
			 * 3.Ȼ��StringBuffer���ó����� ---String
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "TTTT";
		} finally {// ��ִ��
			httpconn.disconnect();// �ر���������
			/**
			 * �׳��������ʱ����ܳ��ֵ��쳣״��
			 */
			try {
				inputs.close();
				b.close();// �رն�ȡ
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// �ر�������
		}

	}

	// �ж��ֻ��Ƿ�����
	public boolean IsNETworkHtttpConnection(Context context) {
		if (context != null) {
			// ��һ��������Ŀ//��ȡ������� ,��ȡ����ϵͳ�Ƿ�������һ��Ȩ��
			// �������ӹ���
			ConnectivityManager CManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = CManager.getActiveNetworkInfo();// ��ȡ����״̬
			if (info != null) {
				return info.isAvailable();// ���������true ,false
			} else {
				Toast.makeText(context, "����û������", 1).show();
				return info.isAvailable();
			}
		}

		return false;
	}

}
