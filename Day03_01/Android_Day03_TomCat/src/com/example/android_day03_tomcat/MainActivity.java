package com.example.android_day03_tomcat;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {

	// 创建一个定时器
		public Handler handler = new Handler();
		private SoundPool sp;
		private HashMap<Integer, Integer> map;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			// 创建一个播放器
			/**
			 * maxStreams 最大播放条数 streamType、 播放音乐的类型
			 */
			sp = new SoundPool(3, AudioManager.STREAM_SYSTEM, 1);
			// 创建SD卡， 创建一个可以存放音频文件的集合
			map = new HashMap<Integer, Integer>();
			// 运用播放器，加载一个音频文件
			// sp.load(this, R.raw.p_knockout3, 1);
			// 将音乐保存到SD中
			map.put(1, sp.load(this, R.raw.p_knockout3, 1));
			map.put(2, sp.load(this, R.raw.p_poke_foot3, 1));
			map.put(3, sp.load(this, R.raw.pillow5, 1));

			View view = findViewById(R.id.breath_layout);// 为了展示呼吸状态的layout
			AnimationDrawable anim_breath = (AnimationDrawable) view
					.getBackground();//
			anim_breath.start();// 开启呼吸的动画
		}

		// 点击眼睛的时候要触发的方法
		public void Start_look(View view) {
			Start_anim(R.drawable.look_anim, 1);
		}

		// 点击眼睛的时候要触发的方法
		public void Start_talk(View view) {
			Start_anim(R.drawable.talk_anim, 2);
		}

		// 点击眼睛的时候要触发的方法
		public void Start_fart(View view) {
			Start_anim(R.drawable.fart_anim, 3);

		}

		// 点击屏幕的时候所触发的方法
		// 快速导包
		// public void Start_fart(View view) {// 获取到：相对布局RelativeLayout
		// // AnimationDrawable 播放器 把胶卷教给播放器
		// AnimationDrawable anim = (AnimationDrawable) view.getBackground();// 获取背景
		// // fart_anim.xml
		// // .获取胶卷
		// anim.start();// 播放胶卷
		// // anim.stop();
		//
		// }
		public void Start_anim(int it1, int it2) {
			/**
			 * 1.先找到播放的舞台 2.给舞台舞台添加节目（添加背景） 3.通过舞台获取胶卷，将胶卷教给播放器 4.设置该动画播放一次（找机器）；
			 * 5.开始播放动画，接着播放声音 6.当动画播放完毕以后，需要让动画退场，显示呼吸的动画 1.什么时候退场（time） 2.计时器
			 * 
			 */
			final View other_layout = findViewById(R.id.other_layout);// 1
			other_layout.setBackgroundResource(it1);// 2
			AnimationDrawable anim = (AnimationDrawable) other_layout
					.getBackground();// 3.look_anim.xml
			anim.setOneShot(true);// 4.播放一次
			anim.start();// 5
			// 播放音乐
			sp.play(map.get(it2), 1, 1, 0, 0, 1);
			int time = 500;// 时间为500毫秒
			// 获取胶卷的帧数（获取图片的张数） //通过遍历获取时间
			for (int i = 0; i < anim.getNumberOfFrames(); i++) {
				time = time + anim.getDuration(i);
			}
			/**
			 * R ==runnable对象要执行的动作 delayMillis 时间
			 */
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// 让动画退场
					// 设置背景透明化
					other_layout.setBackgroundColor(0X000000);
				}
			}, time);
		}

	}
