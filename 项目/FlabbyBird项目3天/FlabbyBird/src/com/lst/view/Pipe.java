
package com.lst.view;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;


public class Pipe {

	// ���¹ܵ���ľ���
	private static final float RADIO_BETWEEN_UP_DOWN = 1F / 5F;// ���ܵľ��� ԭ1.5/5
	// �Ϲܵ������߶�
	private static final float RADIO_MAX_HEIGHT = 2 / 5F;
	// �Ϲܵ�����С�߶�
	private static final float RADIO_MINHEIGHT = 1 / 5F;
	// �ܵ��ĺ�����
	private int pipe_x;
	// �Ϲܵ��ĸ߶�
	private int height;
	// ���¹ܵ���ľ���
	private int margin;
	// �Ϲܵ�ͼƬ
	private Bitmap mTopBitmap;
	// �¹ܵ�ͼƬ
	private Bitmap mBottomBitmap;

	private static Random random = new Random();

	public Pipe(Context context, int gameWidth, int gameHeight, Bitmap top,
			Bitmap bottom) {
		margin = (int) (gameHeight * RADIO_BETWEEN_UP_DOWN);

		// Ĭ�ϴ���߳���
		pipe_x = gameWidth;
		mTopBitmap = top;
		mBottomBitmap = bottom;

		randomHeight(gameHeight);
	}

	// �������һ���߶�
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

	// ����
	public void draw(Canvas mCanvas, RectF rect) {
		mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
		// rectΪ�����ܵ�����������ܵ�Ϊ100����Ҫ����20��������ƫ��80
		mCanvas.translate(pipe_x, -(rect.bottom - height));
		mCanvas.drawBitmap(mTopBitmap, null, rect, null);
		// �¹ܵ�ƫ����Ϊ���Ϲܵ��߶�+margin
		mCanvas.translate(0, rect.bottom + margin);
		mCanvas.drawBitmap(mBottomBitmap, null, rect, null);
		mCanvas.restore();
	}

	// �ж�bird�Ƿ������ܵ�
	public boolean touchBird(Bird bird) {
		if (bird.getBird_x() + bird.getBird_width() > pipe_x
				&& (bird.getBird_y() < height || bird.getBird_y()
						+ bird.getBird_height() > height + margin)) {
			return true;
		}
		return false;
	}
}
