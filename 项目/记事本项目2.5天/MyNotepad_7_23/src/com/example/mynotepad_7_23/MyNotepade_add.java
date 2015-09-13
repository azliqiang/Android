package com.example.mynotepad_7_23;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.SQL.Mynotepad_Help;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MyNotepade_add extends Activity implements OnClickListener {
	private Button btn_nosave;
	private Button btn_save;
	private EditText add_title;
	private Spinner add_spinner;
	private EditText add_body;
	private String stg_weather;

	// 第二个添加页面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置当前界面的现实布局
		setContentView(R.layout.mynotepad_add);
		FindView();// 查找布局上有用的id控件
		InputSpinner();// 为选择下拉框添加可选信息
		add_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 获取选择选项的文字信息
				stg_weather = add_spinner.getItemAtPosition(position)
						.toString();

				Toast.makeText(MyNotepade_add.this, "天气：" + stg_weather, 0)
						.show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void InputSpinner() {
		// TODO Auto-generated method stub
		String[] stg_spinner = { "晴", "多云", "大雨", "雷阵雨", "流星雨" };
		// 0,1,2,3,4
		// 适配器
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, stg_spinner);
		add_spinner.setAdapter(adapter);// 将数据加载到add_spinner

	}

	private void FindView() {
		// TODO Auto-generated method stub
		btn_nosave = (Button) findViewById(R.id.btn_nosave);
		btn_save = (Button) findViewById(R.id.btn_save);
		add_title = (EditText) findViewById(R.id.add_title);
		add_spinner = (Spinner) findViewById(R.id.add_spinner);
		add_body = (EditText) findViewById(R.id.add_body);
		btn_nosave.setOnClickListener(this);// 添加监听
		btn_save.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {// v.getId()获取到被点击控件ID
		case R.id.btn_nosave:
			// 带按钮对话框
			if (add_title.getText().toString().trim().length() == 0
					&& add_body.getText().toString().trim().length() == 0) {
				MyNotepade_add.this.finish();// 当用户没有输入内容时，可以直接退出当前页面
			} else {
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						this);
				dialogBuilder.setTitle("警告提示框");
				dialogBuilder.setMessage("是否要删除当前内容");
				//dialogBuilder.setCancelable(false);
				dialogBuilder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						MyNotepade_add.this.finish();//关闭当前页面
						Toast.makeText(MyNotepade_add.this, "已删除所写内容", 0).show();
					}
				});
				dialogBuilder.setPositiveButton("取消", null);
				dialogBuilder.show();
			}
			break;
		case R.id.btn_save:

			if (add_title.getText().toString().trim().length() == 0
					|| add_body.getText().toString().trim().length() == 0) {
				Toast.makeText(this, "标题和内容不能为空", 1).show();
				return;// 返回当前操作
			}
			String stg_title = add_title.getText().toString().trim();// 获取到标题
			String stg_body = add_body.getText().toString().trim();// 获取到内容

			// 获取当前系统的时间
			Date d = new Date();// 获取当前系统的时间，得到的十一个Long类型的数据
			// 格式化工具将一个long类型的时间转换为年月日，时分秒的格式
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
			String stg_time = sdf.format(d);// 返回一个String类型的字符串
			// 创建一个ContentValues为了将信息存放到ContentValues 进而便于传送值和存储
			// 以键值对的形式保存值
			ContentValues values = new ContentValues();
			values.put("_TITLE", stg_title);
			values.put("_WEATHER", stg_weather);
			values.put("_TIME", stg_time);
			values.put("_BODY", stg_body);
			Mynotepad_Help db = new Mynotepad_Help(this);
			db.Date_add(values);// 调用帮助类中保存的方法
			Toast.makeText(this, "保存成功", 0).show();// 提示保存成功
			this.finish();// 关闭当前页面

			break;

		default:
			break;
		}

	}

}
