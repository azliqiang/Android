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
		 * this.getWindows();当前窗口
		 * setFlags(int,int);便利功能设置中指定的部分标记为旗帜,按setFlags(int,int)。
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN:隐藏所有屏幕装饰(如状态栏),显示这个窗口
		 * 
		 * 
		 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置全屏
        //这是一个方便调用getWindow().requestFeature()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFlabbyBird = new FlabbyBird(this);
        setContentView(mFlabbyBird);
	}

}
