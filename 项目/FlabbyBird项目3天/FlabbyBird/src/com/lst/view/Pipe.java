
package com.lst.view;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


public class Pipe {

	// 上下管道间的距离
	private static final float RADIO_BETWEEN_UP_DOWN = 1F / 5F;// 两管的距离 原1.5/5
	// 上管道的最大高度
	private static final float RADIO_MAX_HEIGHT = 2 / 5F;
	// 上管道的最小高度
	private static final float RADIO_MINHEIGHT = 1 / 5F;
	// 管道的横坐标
	private int pipe_x;
	// 上管道的高度
	private int height;
	// 上下管道间的距离
	private int margin;
	// 上管道图片
	private Bitmap mTopBitmap;
	// 下管道图片
	private Bitmap mBottomBitmap;

	private static Random random = new Random();

	public Pipe(Context context, int gameWidth, int gameHeight, Bitmap top,
			Bitmap bottom) {
		margin = (int) (gameHeight * RADIO_BETWEEN_UP_DOWN);

		// 默认从最边出现
		pipe_x = gameWidth;
		mTopBitmap = top;
		mBottomBitmap = bottom;

		randomHeight(gameHeight);
	}

	// 随机生成一个高度
	public void randomHeight(int gameHeight) {
		height = random
				.nextInt((int) (gameHeight * (RADIO_MAX_HEIGHT - RADIO_MINHEIGHT)));
		height = (int) (height + gameHeight * RADIO_MINHEIGHT);
	}


	public int getPipe_x() {
		return pipe_x;
	}


	public void setPipe_x(int pipe_x) {
		this.pipe_x = pipe_x;
	}

	// 绘制
	public void draw(Canvas mCanvas, RectF rect) {
		mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
		// rect为整个管道，如果完整管道为100，需要绘制20，则向上偏移80
		mCanvas.translate(pipe_x, -(rect.bottom - height));
		mCanvas.drawBitmap(mTopBitmap, null, rect, null);
		// 下管道偏移量为：上管道高度+margin
		mCanvas.translate(0, rect.bottom + margin);
		mCanvas.drawBitmap(mBottomBitmap, null, rect, null);
		mCanvas.restore();
	}

	// 判断bird是否碰到管道
	public boolean touchBird(Bird bird) {
		if (bird.getBird_x() + bird.getBird_width() > pipe_x
				&& (bird.getBird_y() < height || bird.getBird_y()
						+ bird.getBird_height() > height + margin)) {
			return true;
		}
		return false;
	}
}
