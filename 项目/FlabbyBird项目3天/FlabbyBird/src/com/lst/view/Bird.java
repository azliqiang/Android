package com.lst.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


public class Bird {

	private static final float RADIO_POS_HEIGHT = 1/2f;//鸟在屏幕高度的2/3位置
	private static final int BIRD_SIZE = 30;//鸟的宽度30dp
	private int bird_x;//鸟的横坐标x
	private int bird_y;//鸟的纵坐标y
	private int bird_width;//鸟的宽度
	private int bird_height;//鸟的高度
	private Bitmap birdBitmap;//鸟的bitmap
	private RectF birdRect= new RectF();//鸟绘制的范围
	private int mGameHeight;
	
	public Bird(Context context,int gameWidth,int gameHeight,Bitmap bitmap){
		this.birdBitmap = bitmap;
		
		//鸟的位置
		bird_x = gameWidth/2-bitmap.getWidth()/2;
		bird_y = (int)(gameHeight*RADIO_POS_HEIGHT);
		
		//计算鸟的宽度和高度
		bird_width=Util.dp2px(context, BIRD_SIZE);
		bird_height=(int)(bird_width*1.0f/bitmap.getWidth()*bitmap.getHeight());
	}
	
	public void resetHeight()
	{
		bird_y=(int)(mGameHeight*RADIO_POS_HEIGHT);
	}
	
	//绘制
	public void draw(Canvas canvas)
	{
		birdRect.set(bird_x, bird_y, bird_x+bird_width, bird_y+bird_height);
		canvas.drawBitmap(birdBitmap, null, birdRect,null);
	}

	public int getBird_x() {
		return bird_x;
	}

	public void setBird_x(int bird_x) {
		this.bird_x = bird_x;
	}

	public int getBird_y() {
		return bird_y;
	}

	public void setBird_y(int bird_y) {
		this.bird_y = bird_y;
	}


	public int getBird_width() {
		return bird_width;
	}

	public void setBird_width(int bird_width) {
		this.bird_width = bird_width;
	}

	public int getBird_height() {
		return bird_height;
	}

	public void setBird_height(int bird_height) {
		this.bird_height = bird_height;
	}
	
}
