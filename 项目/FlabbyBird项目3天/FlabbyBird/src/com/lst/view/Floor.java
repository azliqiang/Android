package com.lst.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;


public class Floor {
  
private static final float FLOOR_Y_POS_RADIO = 4/5f;//地板位置游戏面板高度的4/5到底部
   
   private int floor_x;//x坐标
   private int floor_y;//y坐标
   
   private BitmapShader mFloorShader;//填充物
   
   private int mGameWidth;
   private int mGameHeight;
   
   public  Floor(int gameWidth,int gameHeight,Bitmap floorBg)
   {
	   mGameWidth = gameWidth;
	   mGameHeight= gameHeight;
	   
	   floor_y=(int)(gameHeight * FLOOR_Y_POS_RADIO);
	   mFloorShader = new BitmapShader(floorBg, TileMode.REPEAT, TileMode.CLAMP);
	   
   }
   
   //绘制
   public void draw(Canvas mCanvas,Paint mPaint)
   {
	   if(-floor_x>mGameWidth)
	   {
		   floor_x=floor_x%mGameWidth;
	   }
	   mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
	   //移动到制定的位置
	   mCanvas.translate(floor_x, floor_y);
	   mPaint.setShader(mFloorShader);
	   mCanvas.drawRect(floor_x, 0, mGameWidth-floor_x, mGameHeight-floor_y, mPaint);
	   mCanvas.restore();
	   mPaint.setShader(null);
   }

  	public int getFloor_x() {
  		return floor_x;
  	}


  	public void setFloor_x(int floor_x) {
  		this.floor_x = floor_x;
  	}


  	public int getFloor_y() {
  		return floor_y;
  	}

  	public void setFloor_y(int floor_y) {
  		this.floor_y = floor_y;
  	}

}
