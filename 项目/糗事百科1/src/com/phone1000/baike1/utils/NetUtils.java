package com.phone1000.baike1.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.phone1000.baike1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetUtils {
	public String httpGet(String url){
		
		HttpParams params=new BasicHttpParams();
		
		HttpConnectionParams.setSoTimeout(params,10*1000);
		HttpConnectionParams.setConnectionTimeout(params,10*1000);

		HttpClient client=new DefaultHttpClient(params);
		
		HttpGet get=new HttpGet(url);
		try {
			HttpResponse response=client.execute(get);
			
			if(response.getStatusLine().getStatusCode()==200){

				HttpEntity entity=response.getEntity();
				String result=EntityUtils.toString(entity);
				return result;
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Bitmap getImage(String strUrl){
		URL url;
		try {
			url = new URL(strUrl);
			URLConnection conn=url.openConnection();
			conn.connect();  
			Bitmap bitmap=BitmapFactory.decodeStream(conn.getInputStream());
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
