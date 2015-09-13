package com.example.thread_game_demo01;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	//
	private RadioGroup radiogroup1;
	private RadioGroup radiogroup2;
	private Button btn_kaishi;
	private TextView tv_jieguo;
	// 目的：为了存储评比规则，
	public HashMap<String, Integer> map = new HashMap<String, Integer>();
	// 创建一个handler 用来跟帮助主线程更改单选按钮的选项
	public Handler handler = new Handler() {
		// 该方法的作用是，接受传过来的信息，并根据信息来执行不同的操作
		public void handleMessage(Message msg) {// msg 变量代表传递过来的信号，
			if (msg.what == 5) {// msg.what 获取信号内容
				Jisuan();// 开始计算结果
			} else {// 0,1,2
				Anim(msg.what);

			}
		}

		private void Jisuan() {
			// TODO Auto-generated method stub
			dialog.dismiss();// 取消对话框的显示。。。
			btn_kaishi.setText("再来玩一次");
			// radiogroup1.getCheckedRadioButtonId();//被选中按钮的id
			// 得到用户操作的选项
			RadioButton radio1 = (RadioButton) findViewById(radiogroup1
					.getCheckedRadioButtonId());
			String stg1 = radio1.getText().toString().trim();// 获取在布局text内容
			// 得到计算机所选择的选项
			RadioButton radio2 = (RadioButton) findViewById(radiogroup2
					.getCheckedRadioButtonId());
			String stg2 = radio2.getText().toString().trim();// 获取在布局text内容

			String stg3 = stg1 + "——" + stg2;

			int it = map.get(stg3);// 1,2,3
			switch (it) {
			case 1:
				tv_jieguo.setText("TMD 又打平，势均力敌，日后在一较高下。。。。");
				break;
			case 2:
				tv_jieguo.setText("唉，又输了，十八年后再战。。。。");
				break;
			case 3:
				tv_jieguo.setText("哈哈哈哈，你是赢不了我的，再去练练吧。。。。");
				break;

			}

		}

		private void Anim(int id) {// 0,1,2
			// TODO Auto-generated method stub
			radiogroup2.clearCheck();// 清空被选中的单选按钮
			// 目标：遍历所有在radiogroup中得单选按钮，并跟传递过来的信号做对比
			for (int i = 0; i < radiogroup2.getChildCount(); i++) {
				// 遍历所有radiogroup2中得单选按钮实体/0,1,2
				RadioButton radiobutton = (RadioButton) radiogroup2
						.getChildAt(i);
				if (i == id) {// 如果遍历的单选按钮跟传递过来的信号对应，就选中该按钮
					radiobutton.setChecked(true);
				}
			}
		};
	};
	private ThreadAnim threadanim;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FindView();// 查找layout布局中得所有有用控件
		InputMap();// 给map集合添加评比规则
		threadanim = new ThreadAnim();// 实例化内部类
		threadanim.start();// 开启线程

	}

	private void InputMap() {
		// TODO Auto-generated method stub
		map.put("剪刀——剪刀", 1);// 1 代表平手
		map.put("剪刀——石头", 2);// 2 代表输
		map.put("剪刀——布", 3);// 3 代表赢

		map.put("石头——剪刀", 3);// 1 代表平手
		map.put("石头——石头", 1);// 2 代表输
		map.put("石头——布", 2);// 3 代表赢

		map.put("布——剪刀", 2);// 1 代表平手
		map.put("布——石头", 3);// 2 代表输
		map.put("布——布", 1);// 3 代表赢

	}

	private void FindView() {
		// TODO Auto-generated method stub
		radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
		radiogroup2 = (RadioGroup) findViewById(R.id.radiogroup2);
		btn_kaishi = (Button) findViewById(R.id.btn_kaishi);
		tv_jieguo = (TextView) findViewById(R.id.tv_jieguo);
		btn_kaishi.setOnClickListener(this);// 将点击事件的方法教给当前类处理
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 内部类 为了实现通过县城不断更替单选按钮--实现计算机自动选择
	class ThreadAnim extends Thread {
		// 设置一个boolean变量，为了显示该线程的运行状态
		public Boolean IsStopThread = false;// false 代表线性没有停止 true 代表线程停止

		// 停止线程的方法
		public void RunStopThread() {
			IsStopThread = true;
		}

		// 给外部提供一个接口，用来让外部了解线程的运行情况
		public boolean ReturnThread() {
			return IsStopThread;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			int index = 0;// 添加一个局部变量，目的是为了记录所选择的单选按钮是哪一个；、0.1.2
			while (!IsStopThread) {
				// 0,1,2,
				if (index >= radiogroup2.getChildCount()) {// radiogroup2.getChildCount()
															// 单选按钮范围中按钮的个数
					index = 0;
				}
				handler.sendEmptyMessage(index++);// 传递信息/0,1,2
				// 100毫秒发送一条
				try {
					Thread.sleep(100);// 让线程睡眠100毫秒后，再循环
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 第一步检查单选按钮有没有选择
		// radiogroup1.getCheckedRadioButtonId() 获取radiogroup中被点击的按钮的id
		// 如果没有被找到的相应id，就会返回-1的值
		if (radiogroup1.getCheckedRadioButtonId() == -1) {
			// 0.代表显示时间极短
			Toast.makeText(this, "请选择您的选项", 0).show();
			return;// 不在继续往下运行
		}
		if (!threadanim.ReturnThread()) {// 查看线程是否正在运行
			threadanim.RunStopThread();// 停止线程的方法
			// 进度条的、提示框
			dialog = new ProgressDialog(this);
			dialog.setTitle("提示框");// 设置标题
			dialog.setMessage("请稍等，正在计算结果。。。。");// 设置提示信息
			dialog.setCancelable(false);// 当对话框显示的时候，用户不能对对话框以外的屏幕进行操作
			dialog.show();// 显示
			new WaitThread().start();// 匿名调用
		}else{
			radiogroup1.clearCheck();
			btn_kaishi.setText("开始比赛");
			tv_jieguo.setText("比赛结果为：。。。。");
			threadanim = new ThreadAnim();
			threadanim.start();
		}
	}

	// 做为一个计时器，多长时间后对话框消失
	class WaitThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				Thread.sleep(1000);// 睡眠一秒后让对话框消失
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			handler.sendEmptyMessage(5);// 给handler传递一个5的暗号

		}

	}

}
