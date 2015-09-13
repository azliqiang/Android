package com.lst.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


public class Bird {

	private static final float RADIO_POS_HEIGHT = 1/2f;//������Ļ�߶ȵ�2/3λ��
	private static final int BIRD_SIZE = 30;//��Ŀ��30dp
	private int bird_x;//��ĺ�����x
	private int bird_y;//���������y
	private int bird_width;//��Ŀ��
	private int bird_height;//��ĸ߶�
	private Bitmap birdBitmap;//���bitmap
	private RectF birdRect= new RectF();//����Ƶķ�Χ
	private int mGameHeight;
	
	public Bird(Context context,int gameWidth,int gameHeight,Bitmap bitmap){
		this.birdBitmap = bitmap;
		
		//���λ��
		bird_x = gameWidth/2-bitmap.getWidth()/2;
		bird_y = (int)(gameHeight*RADIO_POS_HEIGHT);
		
		//������Ŀ�Ⱥ͸߶�
		bird_width=Util.dp2px(context, BIRD_SIZE);
		bird_height=(int)(bird_width*1.0f/bitmap.getWidth()*bitmap.getHeight());
	}
	
	public void resetHeight()
	{
		bird_y=(int)(mGameHeight*RADIO_POS_HEIGHT);
	}
	
	//����
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
