package com.lst.flabbybird;

import com.lst.view.FlabbyBird;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	
	public FlabbyBird mFlabbyBird;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * this.getWindows();��ǰ����
		 * setFlags(int,int);��������������ָ���Ĳ��ֱ��Ϊ����,��setFlags(int,int)��
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN:����������Ļװ��(��״̬��),��ʾ�������
		 * 
		 * 
		 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //����ȫ��
        //����һ���������getWindow().requestFeature()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFlabbyBird = new FlabbyBird(this);
        setContentView(mFlabbyBird);
	}

}
