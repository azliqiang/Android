package com.example.android_day04_lottery;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 
 * @author liushuaitao
 *
 */
public class StartActivity extends Activity implements OnClickListener {

	/**
	 * ������������TextView�ؼ�
	 */
	private TextView textView_num;
	/**
	 * ��������������������
	 */
	private EditText editText_name;
	
	/**
	 * ������Ӳ����������İ�ť
	 */
	private Button button_add;
	
	/**
	 * ������ղ����������İ�ť
	 */
	private Button button_clear;
	
	/**
	 * ������Ϸ��ʼ�İ�ť
	 */
	private Button button_start;
	/**
	 * �����˳���Ϸ�İ�ť
	 */
	private Button button_exit;
	
	/**
	 * �����洢������������map����
	 */
	private HashMap<Integer, String> map=new HashMap<Integer, String>();
	
	/**
	 * ������ʼ����ֵΪ1,֮�����ε���
	 */
	private int key=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.initView();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		this.textView_num=(TextView) this.findViewById(R.id.textView_num);
		this.editText_name=(EditText) this.findViewById(R.id.editText_name);
		
		this.button_add=(Button) this.findViewById(R.id.button_add);
		this.button_add.setOnClickListener(this);
		
		this.button_clear=(Button) this.findViewById(R.id.button_clear);
		this.button_clear.setOnClickListener(this);
		
		this.button_start=(Button) this.findViewById(R.id.button_start);
		this.button_start.setOnClickListener(this);
		
		this.button_exit=(Button) this.findViewById(R.id.button_exit);
		this.button_exit.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
			case R.id.button_add:
				String name=this.editText_name.getText().toString().trim();
				map.put(key, name);
				this.textView_num.setText(String.valueOf(key));
				key++;
				this.editText_name.setText("");
				this.editText_name.requestFocus();
				
				break;
	
			case R.id.button_clear:
				this.editText_name.setText("");
				this.editText_name.requestFocus();
				break;
			case R.id.button_start:
				Intent intent=new Intent(this,MainActivity.class);
				intent.putExtra("map",map);
				intent.putExtra("count",--key);
				this.startActivity(intent);
				this.finish();
				break;
			case R.id.button_exit:
				this.finish();
				break;
		}
		
	}

}
