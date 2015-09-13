package com.example.contact;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
/**
 *  mdpi 1   hdpi 1.5   xhdpi 2  xxhdpi 3
 * @author Administrator
 *
 */
public class AddActivity extends Activity {
	private EditText et_name=null;
	private EditText et_phone=null;
	private GridView gridView=null;
	private List<Integer> list=null;
	private int iconId=-1;
	private ImageView imageView=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		initData();
		
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		
		imageView = (ImageView) findViewById(R.id.imageView_add);
		
		gridView = (GridView) findViewById(R.id.gridView_add);
		AddAdapter adapter=new AddAdapter(this,list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					iconId=list.get(position);
					imageView.setImageResource(iconId);
					Log.i("Add","id="+iconId);
					Log.i("Add","w="+imageView.getWidth()+"--h="+imageView.getHeight());

			}
		});
	}
	
	private void initData() {
		list=new ArrayList<Integer>();
		
		list.add(R.drawable.image1);
		list.add(R.drawable.image2);
		list.add(R.drawable.image3);
		list.add(R.drawable.image4);
		list.add(R.drawable.image5);
		list.add(R.drawable.image6);
		
	}
	public void onAdd(View view){
		String name=et_name.getText().toString().trim();
		String phone=et_phone.getText().toString().trim();
		
		//判断是否位空
		if(TextUtils.isEmpty(name)){
			Toast.makeText(getApplicationContext(),"称呼不能为空",Toast.LENGTH_SHORT).show();
			return;
		}
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
			return;
		}
		if(iconId==-1){
			//表示没有做选择,使用默认图片
			iconId=R.drawable.def;
		}
		//将数据写入到文件中
		if(write(name, phone,iconId+"")){
			Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
		}
	}
	public void onBack(View view){
		finish();
	}
	
	private boolean  write(String name,String phone,String imgId){
		Log.i("Add","imgId="+imgId);
		File file=new File(getFilesDir(),"contact.txt");
		FileOutputStream fos=null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			fos=new FileOutputStream(file,true);
			String str=name+"@"+phone+"@"+imgId+";";
			fos.write(str.getBytes());
			fos.flush();
			return true;
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
