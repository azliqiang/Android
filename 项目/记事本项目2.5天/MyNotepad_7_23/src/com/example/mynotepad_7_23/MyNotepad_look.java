package com.example.mynotepad_7_23;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNotepad_look extends Activity {

	private Button btn_return;
	private TextView look_title;
	private ImageView look_image;
	private TextView look_body;
	private TextView look_weather;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mynotepad_look);
		FindView();
		Intent intent = this.getIntent();
		look_title.setText(intent.getStringExtra("title"));
		look_body.setText(intent.getStringExtra("body"));
		String weather = intent.getStringExtra("weather");
		look_weather.setText("天气："+weather);
		
		if(weather.equals("晴")){
			look_image.setImageResource(R.drawable.d_sunny);
		}else if(weather.equals("小雨")){
			look_image.setImageResource(R.drawable.d_rain);
		}else if(weather.equals("雷阵雨")){
			look_image.setImageResource(R.drawable.d_thunderstorm);
		}else{
			look_image.setImageResource(R.drawable.d_sunny_cloudy);
		}
		btn_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyNotepad_look.this.finish();//关闭当前页面
			}
		});
	}

	private void FindView() {
		// TODO Auto-generated method stub
		
		btn_return = (Button) findViewById(R.id.btn_return);
		look_title = (TextView) findViewById(R.id.look_item);
		look_image = (ImageView) findViewById(R.id.look_image);
		look_body = (TextView) findViewById(R.id.look_body);
		look_weather = (TextView) findViewById(R.id.look_weather);
		
		
	}
}
