package com.train.rock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		final Button button1=(Button) findViewById(R.id.button1);
		final Button button2=(Button) findViewById(R.id.button2);
		
		Animation animation1=AnimationUtils.loadAnimation(this,R.anim.translate_left);
		Animation animation2=AnimationUtils.loadAnimation(this,R.anim.translate_right);
		
		animation1.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				System.out.println("onAnimationStart");
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
				//隐藏两个Button的展示
				button1.setVisibility(View.GONE);
				button2.setVisibility(View.GONE);
				
				//打开PK主界面
				Intent intent=new Intent(StartActivity.this,MainActivity.class);
				startActivity(intent);
				//关闭当前Activity
				finish();
				//overridePendingTransition(0,0);
			}
		});
		
		button1.startAnimation(animation1);
		button2.startAnimation(animation2);
	}
}