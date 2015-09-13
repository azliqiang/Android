package com.example.android_day01_bmi;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NewActivity extends Activity {

	private ImageView image = null;
	private TextView tv_BMI = null;
	private TextView tv_zhuangtai = null;
	private TextView tv_jianyi = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置当前界面的显示视图
		setContentView(R.layout.newactivity);
		// 得到信使身上的信息
		Intent intent = this.getIntent();// 得到当前页面的信使
		double BMI = intent.getDoubleExtra("intent_BMI", 0);
		double shengao = intent.getDoubleExtra("intent_height", 0);
		String sex = intent.getStringExtra("intent_sex");
		// 找到布局上有用的控件
		image = (ImageView) findViewById(R.id.image);
		tv_BMI = (TextView) findViewById(R.id.tv_BMI);
		tv_zhuangtai = (TextView) findViewById(R.id.tv_zhuangtai);
		tv_jianyi = (TextView) findViewById(R.id.tv_jianyi);
		// 给布局上的控件添加值
		// 在代码中这是文本的显示 ；android:text="";
		// 数据格式化工具 将数值保留两位小数
		DecimalFormat df = new DecimalFormat("#0.00");
		//建议健康的体重
		//BMI*(shengao*shengao);
		
		tv_BMI.setText("您测试的BMI值为：\n" + df.format(BMI));//
		if (sex.equals("男")) {
			if (BMI < 18) {// 处于偏瘦状态
				// image.setImageResource(R.drawable.bmi_1);更改显示图片的方法
				image.setImageResource(R.drawable.bmi_1);
				tv_zhuangtai.setText("您目前处于:\n偏瘦状态");
				tv_zhuangtai.setTextColor(Color.BLUE);// android:textColor="#00ff00";
			} else if (BMI >= 18 && BMI < 22) {//
				image.setImageResource(R.drawable.bmi_2);
				tv_zhuangtai.setText("您目前处于:\n健康状态");
				tv_zhuangtai.setTextColor(Color.GREEN);
			} else if (BMI >= 22 && BMI < 25) {//
				image.setImageResource(R.drawable.bmi_3);
				tv_zhuangtai.setText("您目前处于:\n肥胖状态");
				tv_zhuangtai.setTextColor(Color.YELLOW);
			} else {
				image.setImageResource(R.drawable.bmi_4);
				tv_zhuangtai.setText("您目前处于:\n重度肥胖状态");
				tv_zhuangtai.setTextColor(Color.RED);
			}
			double min = 18*(shengao*shengao);//最低体重
			double max = 22*(shengao*shengao);//最低体重
			tv_jianyi.setText("建议您的体重保持在：\n "+df.format(min)+"kg——"+df.format(max)+"kg\n之间");

		} else {
			if (BMI < 16) {// 处于偏瘦状态
				image.setImageResource(R.drawable.bmi_1);
				tv_zhuangtai.setText("您目前处于:\n偏瘦状态");
				tv_zhuangtai.setTextColor(Color.BLUE);// android:textColor="#00ff00";
			} else if (BMI >= 16 && BMI < 20) {//
				image.setImageResource(R.drawable.bmi_2);
				tv_zhuangtai.setText("您目前处于:\n健康状态");
				tv_zhuangtai.setTextColor(Color.GREEN);
			} else if (BMI >= 20 && BMI < 23) {//
				image.setImageResource(R.drawable.bmi_3);
				tv_zhuangtai.setText("您目前处于:\n肥胖状态");
				tv_zhuangtai.setTextColor(Color.YELLOW);
			} else {
				image.setImageResource(R.drawable.bmi_4);
				tv_zhuangtai.setText("您目前处于:\n重度肥胖状态");
				tv_zhuangtai.setTextColor(Color.RED);
			}

			double min = 16*(shengao*shengao);//最低体重
			double max = 20*(shengao*shengao);//最低体重
			tv_jianyi.setText("建议您的体重保持在：\n "+df.format(min)+"kg——"+df.format(max)+"kg\n之间");
		}

	}

}
