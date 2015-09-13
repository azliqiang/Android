
package com.lst.view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.chen.flabbybird.R;


public class FlabbyBird extends SurfaceView implements Runnable, Callback {

	private SurfaceHolder mHolder;
	private Canvas mCanvas;// 与SurfaceHolder绑定的Canvas
	private Thread mthread;// 用于绘制的线程
	private boolean isRunning;// 线程的控制开关

	//
	private Paint mPaint;

	// 当前View的尺寸
	private int mWidth;
	private int mHeight;
	private RectF mGamePanelRect = new RectF();

	private Bitmap mBg;// 背景

	// 按钮
	private Bitmap mBtnSet;// 设置
	private Bitmap mBtnModel;// 模式
	private RectF mBsRect = new RectF();// 按钮绘制的范围
	private RectF mBmRect = new RectF();// 按钮绘制的范围

	// 鸟
	private Bird mBird;
	private Bitmap mBirdBitmap;

	// 地板
	private Floor mFloor;
	private Bitmap mFloorBitmap;

	// 管道
	private static final int PIPE_WIDTH = 60;// 管道的宽度60
	private List<Pipe> mPipe = new ArrayList<Pipe>();

	private Bitmap mPipeTop;
	private Bitmap mPipeBottom;
	private RectF mPipeRect;
	private int mPipeWidth;

	// 分数
	private final int[] mScoreNmus = new int[] { R.drawable.n0, R.drawable.n1,
			R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5,
			R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9 };
	private Bitmap[] mScoreNumBitmap;

	private int mGrade = 0;
	private int mRemovedPipe = 0;

	private static final float RADIO_SINGLE_NUM_HEIGHT = 1 / 15f;
	private int mSingleGradeWidth;
	private int mSingleGradeHeight;
	private RectF mSingleNumRectF;

	private int mSpeed = Util.dp2px(getContext(), 4);//

	private int spSpeed_location = 0;
	private int spDownSpeed_location = 0;
	private int spPipe_location = 0;

	int temp1 = spSpeed_location;
	int temp2 = spDownSpeed_location;
	int temp3 = spPipe_location;

	/*
	 * 游戏状态
	 */
	private enum GameStatus {
		WAITTING, RUNNING, STOP;
	}

	private GameStatus mStatus = GameStatus.WAITTING;

	// 触摸上升的距离，因为是上升，所以为负值
	private static final int TOUCH_UP_SIZE = -16;

	// 将上升的距离转化为px,这里多存储一个变量，改变量在run中计算
	private final int mBirdUpDis = Util.dp2px(getContext(), TOUCH_UP_SIZE);
	private int mTmpBirdDis;

	// 鸟自动下落的距离
	private int mAutoDownSpeed = Util.dp2px(getContext(), 2);//

	// 两个管道间距离
	private int PIPE_DIS_BETWEEN_TWO = Util.dp2px(getContext(), 300);//

	// 记录移动的距离，达到PIPE_DIS_BETWEEN_TWO则生成一个管道
	private int mTmpMoveDistance;

	// 记录需要移除的管道
	private List<Pipe> mNeedRemovePipe = new ArrayList<Pipe>();

	public FlabbyBird(Context context) {
		this(context, null);
	}

	public FlabbyBird(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mHolder = getHolder();
		mHolder.addCallback(this);

		this.setZOrderOnTop(true);// 设置画布背景透明
		mHolder.setFormat(PixelFormat.TRANSLUCENT);

		// 设置可获取焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		// 设置常亮
		setKeepScreenOn(true);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);

		initBitmaps();

		mPipeWidth = Util.dp2px(getContext(), PIPE_WIDTH);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 开启线程
		isRunning = true;
		mthread = new Thread(this);
		mthread.start();
	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 线程关闭
		isRunning = false;

	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRunning) {
			long start = System.currentTimeMillis();
			logic();
			draw();
			long end = System.currentTimeMillis();

			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}// if
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}// while
	}

	public void draw() {
		try {
			mCanvas = mHolder.lockCanvas();// 获取画布
			if (mCanvas != null) {
				drawBg();
				drawBird();
				drawPipe();
				drawFloor();
				drawGrades();
				drawButton();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCanvas != null) {
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}

	// 绘制背景
	private void drawBg() {
		mCanvas.drawBitmap(mBg, null, mGamePanelRect, null);
	}

	// 绘制鸟
	private void drawBird() {
		mBird.draw(mCanvas);
	}

	// 绘制管道
	private void drawPipe() {
		for (Pipe pipe : mPipe) {
			pipe.draw(mCanvas, mPipeRect);
		}
	}

	// 绘制地板
	private void drawFloor() {
		mFloor.draw(mCanvas, mPaint);
	}

	private void drawButton() {
		mBsRect.set(1f / 5 * mWidth, 9f / 10 * mHeight, 1f / 5 * mWidth
				+ mBtnSet.getWidth(), 9f / 10 * mHeight + mBtnModel.getHeight()
				+ 20);
		mCanvas.drawBitmap(mBtnSet, null, mBsRect, null);
		mBmRect.set(3f / 5 * mWidth, 9f / 10 * mHeight, 3f / 5 * mWidth
				+ mBtnModel.getWidth(),
				9f / 10 * mHeight + mBtnModel.getHeight() + 20);
		mCanvas.drawBitmap(mBtnModel, null, mBmRect, null);
	}

	// 绘制分数
	private void drawGrades() {
		String grade = mGrade + "";
		mCanvas.save(Canvas.MATRIX_SAVE_FLAG);
		mCanvas.translate(mWidth / 2 - grade.length() * mSingleGradeWidth / 2,
				1f / 8 * mHeight);

		// 一个一个的绘制分数的图案
		for (int i = 0; i < grade.length(); i++) {
			String scoreStr = grade.substring(i, i + 1);
			int number = Integer.valueOf(scoreStr);
			mCanvas.drawBitmap(mScoreNumBitmap[number], null, mSingleNumRectF,
					null);
			mCanvas.translate(mSingleGradeWidth, 0);
		}
		mCanvas.restore();
	}

	// 根据图片id加载图片
	private Bitmap loadImageByResId(int resId) {
		return BitmapFactory.decodeResource(getResources(), resId);
	}

	// 处理一些逻辑上的计算
	public void logic() {
		switch (mStatus) {
		case RUNNING:
			mGrade = 0;
			// 更新地板绘制的X坐标，地板移动
			mFloor.setFloor_x(mFloor.getFloor_x() - mSpeed);

			logicPipe();

			// 默认下落，点击时瞬间上升
			mTmpBirdDis = mTmpBirdDis + mAutoDownSpeed;
			mBird.setBird_y(mBird.getBird_y() + mTmpBirdDis);

			// 计算分数
			mGrade = mGrade + mRemovedPipe;
			for (Pipe pipe : mPipe) {
				if (pipe.getPipe_x() + mPipeWidth < mBird.getBird_x()) {
					mGrade++;
				}
			}
			checkGameOver();
			break;
		case STOP:// 鸟落下
			// 如果鸟还在空中，先让它掉下来
			if (mBird.getBird_y() < mFloor.getFloor_y() - mBird.getBird_width()) {
				mTmpBirdDis = mTmpBirdDis + mAutoDownSpeed;
				mBird.setBird_y(mBird.getBird_y() + mTmpBirdDis);
			} else {
				mStatus = GameStatus.WAITTING;
				initPos();
			}
			break;
		default:
			break;

		}
	}

	// 处理管道的逻辑计算
	private void logicPipe() {
		// 管道移动
		for (Pipe pipe : mPipe) {
			if (pipe.getPipe_x() < -mPipeWidth) {
				mNeedRemovePipe.add(pipe);
				mRemovedPipe++;
				continue;
			}
			pipe.setPipe_x(pipe.getPipe_x() - mSpeed);

		}

		// 移动管道
		mPipe.removeAll(mNeedRemovePipe);
		mNeedRemovePipe.clear();

		// 管道
		mTmpMoveDistance = mTmpMoveDistance + mSpeed;
		// 生成一个管道
		if (mTmpMoveDistance >= PIPE_DIS_BETWEEN_TWO) {
			Pipe pipe = new Pipe(getContext(), getWidth(), getHeight(),
					mPipeTop, mPipeBottom);
			mPipe.add(pipe);
			mTmpMoveDistance = 0;
		}

	}

	private void initBitmaps() {
		mBg = loadImageByResId(R.drawable.bg1);
		mBirdBitmap = loadImageByResId(R.drawable.b1);
		mFloorBitmap = loadImageByResId(R.drawable.floor_bg2);
		mPipeTop = loadImageByResId(R.drawable.g2);
		mPipeBottom = loadImageByResId(R.drawable.g1);
		mBtnSet = loadImageByResId(R.drawable.btn1);
		mBtnModel = loadImageByResId(R.drawable.btn2);

		mScoreNumBitmap = new Bitmap[mScoreNmus.length];
		for (int i = 0; i < mScoreNumBitmap.length; i++) {
			mScoreNumBitmap[i] = loadImageByResId(mScoreNmus[i]);
		}
	}

	// 重置鸟的位置等数据
	public void initPos() {
		mPipe.clear();
		// 立即增加一个
		mPipe.add(new Pipe(getContext(), getWidth(), getHeight(), mPipeTop,
				mPipeBottom));
		mNeedRemovePipe.clear();
		// 重置鸟的位置
		mBird.resetHeight();
		// 重置下落速度
		mTmpBirdDis = 0;
		mTmpMoveDistance = 0;
		mRemovedPipe = 0;
	}

	public void checkGameOver() {
		// 判断是否碰到地板
		if (mBird.getBird_y() > mFloor.getFloor_y() - mBird.getBird_height()) {
			mStatus = GameStatus.STOP;
		}

		// 如果碰到管道
		for (Pipe pipe : mPipe) {
			// 判断是否穿过
			if (pipe.getPipe_x() + mPipeWidth < mBird.getBird_x()) {
				continue;
			}

			if (pipe.touchBird(mBird)) {
				mStatus = GameStatus.STOP;
				break;
			}

		}// for
	}

	/*
	 * 初始化尺寸相关
	 * 
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;
		mGamePanelRect.set(0, 0, w, h);

		// 初始化mBird
		mBird = new Bird(getContext(), mWidth, mHeight, mBirdBitmap);

		// 初始化地板
		mFloor = new Floor(mWidth, mHeight, mFloorBitmap);

		// 初始化管道范围
		mPipeRect = new RectF(0, 0, mPipeWidth, mHeight);

		Pipe pipe = new Pipe(getContext(), w, h, mPipeTop, mPipeBottom);
		mPipe.add(pipe);

		// 初始化分数
		mSingleGradeHeight = (int) (h * RADIO_SINGLE_NUM_HEIGHT);
		mSingleGradeWidth = (int) (mSingleGradeHeight * 1.0f
				/ mScoreNumBitmap[0].getHeight() * mScoreNumBitmap[0]
				.getWidth());
		mSingleNumRectF = new RectF(0, 0, mSingleGradeWidth, mSingleGradeHeight);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {

			// 设置按钮
			if (event.getX() > 1f / 5 * mWidth
					&& event.getX() < 1f / 5 * mWidth + mBtnSet.getWidth()
					&& event.getY() > 9f / 10 * mHeight
					&& event.getY() < 9f / 10 * mHeight + mBtnSet.getHeight()
							+ 20) {
				createSetDiaglog();
				return true;
			}

			// 模式按钮
			if (event.getX() > 3f / 5 * mWidth
					&& event.getX() < 3f / 5 * mWidth + mBtnModel.getWidth()
					&& event.getY() > 9f / 10 * mHeight
					&& event.getY() < 9f / 10 * mHeight + mBtnModel.getHeight()
							+ 20) {

				return true;
			}

			switch (mStatus) {
			case WAITTING:
				mStatus = GameStatus.RUNNING;
				break;
			case RUNNING:
				mTmpBirdDis = mBirdUpDis;
				break;
			default:
				break;
			}
		}

		return true;

	}

	//设置的对话框
	public void createSetDiaglog() {

		temp1 = spSpeed_location;
		temp2 = spDownSpeed_location;
		temp3 = spPipe_location;

		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View lView = layoutInflater.inflate(R.layout.set_activity, null);

		AlertDialog.Builder setDialog = new Builder(getContext());
		setDialog.setView(lView);
		Spinner spSpeed = (Spinner) lView.findViewById(R.id.spinnerspeed);// 速度
		Spinner spDownSpeed = (Spinner) lView
				.findViewById(R.id.spinnerdownspeed);// 鸟的下落速度
		Spinner spPipe = (Spinner) lView.findViewById(R.id.spinnerpipe);// 两管的距离

		final String mSpeedm[] = { "2", "4","8","16" };
		final String mDownSpeedm[] = { "2", "4", "8" };
		final String mPipem[] = { "300", "200", "100"};

		spSpeed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				temp1 = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spDownSpeed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				temp2 = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spPipe.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				temp3 = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		ArrayAdapter<String> adSpeed = new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_spinner_item, mSpeedm);
		ArrayAdapter<String> adDownSpeed = new ArrayAdapter<String>(
				getContext(), android.R.layout.simple_spinner_item, mDownSpeedm);
		ArrayAdapter<String> adPipe = new ArrayAdapter<String>(getContext(),
				android.R.layout.simple_spinner_item, mPipem);

		adSpeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adDownSpeed
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adPipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spSpeed.setAdapter(adSpeed);
		spDownSpeed.setAdapter(adDownSpeed);
		spPipe.setAdapter(adPipe);

		spSpeed.setSelection(spSpeed_location, true);
		spDownSpeed.setSelection(spDownSpeed_location, true);
		spPipe.setSelection(spPipe_location, true);

		setDialog.setTitle("游戏参数设置");
		setDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						spSpeed_location = temp1;
						spDownSpeed_location = temp2;
						spPipe_location = temp3;

						mSpeed = Util.dp2px(getContext(),
								Integer.parseInt(mSpeedm[spSpeed_location]));
						mAutoDownSpeed = Util.dp2px(getContext(), Integer
								.parseInt(mDownSpeedm[spDownSpeed_location]));
						PIPE_DIS_BETWEEN_TWO = Util.dp2px(getContext(),
								Integer.parseInt(mPipem[spPipe_location]));

					}

				});
		setDialog.setNegativeButton("取消", null);
		setDialog.create().show();

	}

}
