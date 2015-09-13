package com.example.android_day01_bmi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 
 * @author liushuaitao 
 * Activity 一个activity相当于一个显示界面，而一个显示界面需要一个显示视图
 */
public class MainActivity extends Activity {

	private EditText ed_height = null;
	private EditText ed_weight = null;
	private RadioGroup sex_group = null;
	private Button btn_ceshi = null;
	private Button btn_chongzhi = null;
	public String sex = "男";// 性别默认为：男

	/**
	 * onCreate 启动activity的时候，调用的方法
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置当前界面的显示视图
		setContentView(R.layout.activity_main);
		// it = (int)1.1;
		ed_height = (EditText) this.findViewById(R.id.ed_height);// 身高的输入框
		ed_weight = (EditText) this.findViewById(R.id.ed_weight);
		sex_group = (RadioGroup) findViewById(R.id.sex_group);
		btn_ceshi = (Button) findViewById(R.id.btn_ceshi);
		btn_chongzhi = (Button) findViewById(R.id.btn_chongzhi);

		// 对单选按钮的范围做监听==RadioGroup
		sex_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			/**
			 * checkedId ==单选按钮的ID
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.woman:
					sex = "女";
					// context 上下文菜单 text=要显示的内容 duration = 显示时间
					Toast.makeText(MainActivity.this, "性别：" + sex, 1).show();
					break;
				case R.id.man:
					sex = "男";
					Toast.makeText(MainActivity.this, "性别：" + sex, 1).show();
					break;
				// 当没有对应的选项时；会触发default
				default:
					break;
				}
			}
		});

		// 给按钮添加监听事件
		// 点击btn_ceshi .就会触发onclick方法
		btn_ceshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ed_height.getText().toString() 获取输入框中得内容，转换成String字符串
				// trim().length() .去除前后空格，获取字符串长度
				if (ed_height.getText().toString().trim().length() == 0) {
					ed_height.setError("请输入身高");
					return;// 不再继续往下运行，返回当前操作
				}
				if (ed_weight.getText().toString().trim().length() == 0) {
					ed_weight.setError("请输入体重");
					return;// 不再继续往下运行，返回当前操作
				}
				// 提取输入框中的内容 Double.valueOf(d);将一个其他类型的值，转变成一个double
				double d_height = Double.valueOf(ed_height.getText()
						.toString().trim()) / 100;
				double d_weight = Double.valueOf(ed_weight.getText()
						.toString().trim());
				// BMI的运算 BMI = 体重(kg)/(身高(m)*身高(m))；
				double BMI = d_weight / (d_height * d_height);

				Toast.makeText(
						MainActivity.this,
						"身高：" + d_height + ",体重：" + d_weight + ".性别：" + sex
								+ ",BMI=" + BMI, 1).show();
				/**
				 * Intent 信使 传递信息
				 * 
				 * 启动activity 1.启动源 2，启动目标
				 */
				Intent intent = new Intent(MainActivity.this, NewActivity.class);
				// 添加一个额外信息键值对的形式存储值
				intent.putExtra("intent_BMI", BMI);
				intent.putExtra("intent_height", d_height);
				intent.putExtra("intent_sex", sex);
				startActivity(intent);// 启动Intent

			}
		});

	}

	//重置的方法
	public void Start_Delete(View v) {
		ed_height.setText("");
		ed_weight.setText("");
		sex = "男";
		RadioButton nan = (RadioButton) findViewById(R.id.man);
		nan.setChecked(true);// 设置选中状态

	}
}