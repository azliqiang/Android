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

	// ����һ����ʱ��
		public Handler handler = new Handler();
		private SoundPool sp;
		private HashMap<Integer, Integer> map;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			// ����һ��������
			/**
			 * maxStreams ��󲥷����� streamType�� �������ֵ�����
			 */
			sp = new SoundPool(3, AudioManager.STREAM_SYSTEM, 1);
			// ����SD���� ����һ�����Դ����Ƶ�ļ��ļ���
			map = new HashMap<Integer, Integer>();
			// ���ò�����������һ����Ƶ�ļ�
			// sp.load(this, R.raw.p_knockout3, 1);
			// �����ֱ��浽SD��
			map.put(1, sp.load(this, R.raw.p_knockout3, 1));
			map.put(2, sp.load(this, R.raw.p_poke_foot3, 1));
			map.put(3, sp.load(this, R.raw.pillow5, 1));

			View view = findViewById(R.id.breath_layout);// Ϊ��չʾ����״̬��layout
			AnimationDrawable anim_breath = (AnimationDrawable) view
					.getBackground();//
			anim_breath.start();// ���������Ķ���
		}

		// ����۾���ʱ��Ҫ�����ķ���
		public void Start_look(View view) {
			Start_anim(R.drawable.look_anim, 1);
		}

		// ����۾���ʱ��Ҫ�����ķ���
		public void Start_talk(View view) {
			Start_anim(R.drawable.talk_anim, 2);
		}

		// ����۾���ʱ��Ҫ�����ķ���
		public void Start_fart(View view) {
			Start_anim(R.drawable.fart_anim, 3);

		}

		// �����Ļ��ʱ���������ķ���
		// ���ٵ���
		// public void Start_fart(View view) {// ��ȡ������Բ���RelativeLayout
		// // AnimationDrawable ������ �ѽ���̸�������
		// AnimationDrawable anim = (AnimationDrawable) view.getBackground();// ��ȡ����
		// // fart_anim.xml
		// // .��ȡ����
		// anim.start();// ���Ž���
		// // anim.stop();
		//
		// }
		public void Start_anim(int it1, int it2) {
			/**
			 * 1.���ҵ����ŵ���̨ 2.����̨��̨��ӽ�Ŀ����ӱ����� 3.ͨ����̨��ȡ����������̸������� 4.���øö�������һ�Σ��һ�������
			 * 5.��ʼ���Ŷ��������Ų������� 6.��������������Ժ���Ҫ�ö����˳�����ʾ�����Ķ��� 1.ʲôʱ���˳���time�� 2.��ʱ��
			 * 
			 */
			final View other_layout = findViewById(R.id.other_layout);// 1
			other_layout.setBackgroundResource(it1);// 2
			AnimationDrawable anim = (AnimationDrawable) other_layout
					.getBackground();// 3.look_anim.xml
			anim.setOneShot(true);// 4.����һ��
			anim.start();// 5
			// ��������
			sp.play(map.get(it2), 1, 1, 0, 0, 1);
			int time = 500;// ʱ��Ϊ500����
			// ��ȡ�����֡������ȡͼƬ�������� //ͨ��������ȡʱ��
			for (int i = 0; i < anim.getNumberOfFrames(); i++) {
				time = time + anim.getDuration(i);
			}
			/**
			 * R ==runnable����Ҫִ�еĶ��� delayMillis ʱ��
			 */
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// �ö����˳�
					// ���ñ���͸����
					other_layout.setBackgroundColor(0X000000);
				}
			}, time);
		}

	}
