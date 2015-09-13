package com.example.android_day04_lottery;

import java.util.HashMap;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author liushuaitao
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	/**
	 * 声明存放参赛人数的map
	 */
	private HashMap<Integer, String> map;

	/**
	 * 声明存储参赛人数的变量
	 */
	private int count;

	/**
	 * 声明中奖人数
	 */
	private int prizeNum;

	/**
	 * 声明进度对话框
	 */
	private ProgressDialog progressDialog;

	/**
	 * 声明获奖人数输入框
	 */
	private EditText editText_prize;

	/**
	 * 声明参与人数的输入框
	 */
	private EditText editText_totalNum;

	/**
	 * 声明确定按钮
	 */
	private Button button_ok;

	/**
	 * 声明开始抽奖按钮
	 */
	public Button button_start;

	/**
	 * 声明显示中奖人的姓名的控件
	 */
	private TextView textView_name;

	/**
	 * 声明抽奖动画控件
	 */
	private ImageView imageView_bg;

	/**
	 * 声明存放获奖人名字的索引值
	 */
	private int[] prizeArray;

	/**
	 * 声明默认获奖编号
	 */
	private int prizeId;

	/**
	 * 声明抽奖次数
	 */
	private int prizeTime;

	/**
	 * 声明动画对象
	 */
	private AnimationDrawable animationDrawable_imageView;
	private AnimationDrawable animationDrawable_button;

	/**
	 * 声明抽奖线程对象
	 */
	private MyThread myThread;

	/**
	 * 声明是否正在抽奖的标志
	 */
	private boolean isPrizing = true;

	/**
	 * 声明处理器负责子线程和主线程之间的通讯
	 */

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				// 停止锟介奖
				myThread.stopThread();
				// 锟皆伙拷锟斤拷锟斤拷失
				progressDialog.dismiss();
				if (prizeTime < prizeNum) {
					String name = map.get(prizeId);
					textView_name.setText(name);
					textView_name.setTextColor(Color.RED);
					prizeArray[prizeTime] = prizeId;
					prizeTime++;

				} else {
					isPrizing = false;
					button_start.setClickable(false);
					textView_name.setText("抽奖完毕");
					textView_name.setTextColor(Color.YELLOW);
				}
			} else {
				sweepStake(msg.what);
			}
		}

		/**
		 * 锟介奖
		 * 
		 * @param what
		 */
		private void sweepStake(int what) {
			if (isPrizing) {
				// 锟矫碉拷锟斤拷锟斤拷榻憋拷说锟斤拷锟斤拷锟�
				String name = map.get(what);
				textView_name.setText(name);
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = this.getIntent();
		map = (HashMap<Integer, String>) intent.getSerializableExtra("map");
		count = intent.getIntExtra("count", 0);

		this.initView();
		this.startImageViewAnimation();

	}

	/**
	 * 锟斤拷锟斤拷ImageView锟斤拷锟斤拷
	 */
	private void startImageViewAnimation() {
		animationDrawable_imageView = (AnimationDrawable) this.imageView_bg
				.getBackground();
		animationDrawable_imageView.start();
	}

	/**
	 * 锟斤拷始锟斤拷锟截硷拷
	 * 
	 */
	private void initView() {
		this.editText_prize = (EditText) this.findViewById(R.id.editText_prize);
		this.editText_prize.requestFocus();

		this.editText_totalNum = (EditText) this
				.findViewById(R.id.editText_totalNum);
		this.editText_totalNum.setText(String.valueOf(count));

		this.textView_name = (TextView) this.findViewById(R.id.textView_name);

		this.imageView_bg = (ImageView) this.findViewById(R.id.imageView_bg);

		this.button_ok = (Button) this.findViewById(R.id.button_ok);
		this.button_ok.setOnClickListener(this);

		this.button_start = (Button) this.findViewById(R.id.button_start);
		this.button_start.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.button_ok:
			this.prizeNum = Integer.parseInt(this.editText_prize.getText()
					.toString().trim());
			if (prizeNum > count) {
				this.editText_prize.setError("锟斤锟斤拷锟斤拷!");
				this.editText_prize.setText("");
				this.editText_prize.requestFocus();
				return;
			}

			this.prizeArray = new int[this.prizeNum];
			this.editText_prize.setFocusable(false);

			// 锟斤拷锟斤拷锟斤拷始锟介奖锟斤拷钮锟斤拷锟斤拷
			this.animationDrawable_button = (AnimationDrawable) this.button_start
					.getBackground();
			this.animationDrawable_button.start();
			

			this.button_ok.setClickable(false);
			this.button_start.setClickable(true);

			// 实锟斤拷锟斤拷锟斤拷始锟介奖锟竭筹拷
			this.myThread = new MyThread();
			this.myThread.start();
			break;

		case R.id.button_start:
			if (!myThread.isStop()) {
				// 停止锟介奖锟斤拷锟斤拷
				animationDrawable_button.stop();
				progressDialog = new ProgressDialog(this);
				progressDialog.setTitle("正在计算结果");
				progressDialog.setMessage("请等待5秒钟....");
				progressDialog.setCancelable(false);
				progressDialog.show();

				// 锟斤拷始锟斤拷锟斤拷时
				new TimerThread().start();
			} else {
				// 锟斤拷始锟介奖锟斤拷锟斤拷
				animationDrawable_button.start();
				this.myThread = new MyThread();
				this.myThread.start();
				this.isPrizing = true;
				textView_name.setTextColor(Color.BLACK);
			}
			break;
		}
	}

	/**
	 * 锟介奖锟斤拷锟斤拷时
	 * 
	 * 
	 */
	private final class TimerThread extends Thread {
		@Override
		public void run() {
			try {
				// 锟接筹拷5锟斤拷
				Thread.sleep(5000);
				// 锟斤拷锟斤拷锟介奖
				handler.sendEmptyMessage(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 锟皆讹拷锟藉开始锟介奖锟竭筹拷
	 * 
	 * 
	 */
	private final class MyThread extends Thread {
		/**
		 * 
		 * 锟斤拷锟斤拷锟竭筹拷锟角凤拷停止锟侥憋拷志
		 */
		private boolean isStop=false;

		/**
		 * 停止锟竭程碉拷锟斤拷锟斤拷
		 */
		public void stopThread() {
			this.isStop = true;
		}

		/**
		 * 锟斤拷锟斤拷锟竭筹拷锟角凤拷停止锟侥憋拷志
		 * 
		 * @return
		 */
		public boolean isStop() {
			return isStop;
		}
		
		public void RunRandom(){
			// 锟矫碉拷1锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟斤拷锟斤拷
			int randomNum = 0;
			boolean flag = false;
			
			// 锟斤拷锟斤拷锟斤拷锟介，锟介看锟角凤拷锟窖撅拷锟叫斤拷
			do {
				flag = false;
				randomNum = new Random().nextInt(count) + 1;
					for(int i=0;i<prizeArray.length;i++){
						if(randomNum==prizeArray[i]){
							flag = true;
						}
					}
			} while (flag);

			// 锟斤拷锟斤拷锟窖∪★拷谋锟脚存储锟斤拷全锟街憋拷锟斤拷锟斤拷
			prizeId = randomNum;

			handler.sendEmptyMessage(randomNum);
		}

		@Override
		public void run() {
			// 锟斤拷锟斤拷锟角凤拷锟叫斤拷锟侥憋拷志
			 
			while (!this.isStop) {
				RunRandom();

				try {
					// 每锟斤拷50锟斤拷锟斤拷锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
