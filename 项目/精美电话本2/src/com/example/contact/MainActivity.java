package com.example.contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainActivity extends Activity {
	private List<Bean> list=null;
	private GridView gridView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		initListener();
		
	}
	
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridView);
	}
	
	private void initListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//点击的是最后一个则是添加，是其他的则是拨打电话
				if(position==list.size()-1){
					//是添加
					Intent intent=new Intent(MainActivity.this,AddActivity.class);
					startActivity(intent);
				}else{
					Intent intent=new Intent(Intent.ACTION_CALL);
					Uri data=Uri.parse("tel://"+list.get(position).getPhone());
					intent.setData(data);
					startActivity(intent);
				}
			}
		});
	}


	private void update(){
		list=read();
//		for(Bean bean:list){
//			Log.i("Add","bean="+bean.toString());
//		}
		
		MainAdapter adapter=new MainAdapter(list,this);
		gridView.setAdapter(adapter);
//		View view=gridView.findViewById(2);
//		TextView tv=(TextView) view.findViewById(R.id.tv_item);
//		
//		Toast.makeText(this,"text="+tv.getText(),Toast.LENGTH_SHORT).show();
	}
	
	private List<Bean> read() {
		FileInputStream fis=null;
		List<Bean> temp=new ArrayList<Bean>();
		try {
			File file=new File(getFilesDir(),"contact.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			fis=new FileInputStream(file);
			
			byte[] buffer=new byte[fis.available()];
			fis.read(buffer);
			
			String str=new String(buffer);
			Log.i("Add","str="+str);
			if(!TextUtils.isEmpty(str)){
				String[] strs=str.split(";");
				for(int i=0;i<strs.length;i++){
					String[] tempStr=strs[i].split("@");
					Bean bean=new Bean();
					bean.setName(tempStr[0]);
					bean.setPhone(tempStr[1]);
					bean.setImgId(tempStr[2]);
					temp.add(bean);
				}
			}
			Bean bean=new Bean();
			bean.setImgId(R.drawable.add+"");
			bean.setName("添加");
			temp.add(bean);
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return temp;
	} 
	@Override
	protected void onStart() {
		super.onStart();
		update();
	}
}
