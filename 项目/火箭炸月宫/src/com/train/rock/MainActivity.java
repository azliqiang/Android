package com.train.rock;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageView imageView=null;
	private ImageView imageView1=null;
	
	private Button button_fire=null;
	private Button button_send=null;
	private AnimationDrawable drawable=null;
	private AnimationDrawable drawable1=null;
	private Animation animation;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		
		imageView = (ImageView) findViewById(R.id.imageView);
		imageView1=(ImageView) findViewById(R.id.imageView1);
		
		button_fire = (Button) findViewById(R.id.button_fire);
		button_send = (Button) findViewById(R.id.button_send);
		
		drawable = (AnimationDrawable) imageView.getBackground();
		drawable1=(AnimationDrawable) imageView1.getBackground();
		
		animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//动画结束后
				imageView1.setVisibility(View.VISIBLE);
				drawable1.start();
				
				//回收火箭对象
				drawable=null;
				//控件不可点击 
				button_fire.setEnabled(false);
				button_send.setEnabled(false);
				//隐藏控件
				button_fire.setVisibility(View.GONE);
				button_send.setVisibility(View.GONE);
			}
		});
		
		button_fire.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//启动
				if(drawable.isRunning()){
					Toast.makeText(getApplicationContext(),"已经点火，无需在点",Toast.LENGTH_SHORT).show();
				}else{
					drawable.start();
				}
			}
		});
		button_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//发送
				if(drawable.isRunning()){
					imageView.startAnimation(animation);
				}else{
					Toast.makeText(getApplicationContext(),"先点火，在发射",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
