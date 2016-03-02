package com.azalea.www.hotchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Panoo on 2015/12/15.
 */
public class CircleChart extends View {

	private int[] mDefaultColor = {Color.parseColor("#1ead46"), Color.parseColor("#f1e34b"), Color.parseColor("#ff4f00")};
	private int mSepColor = Color.parseColor("#eeeeee");
	private int mInnerCircleColor = Color.parseColor("#ffffff");

	private static final float FULL_PERCENT = 100f;

	private int[] mCharColor = mDefaultColor;

	private int mSartAngle = 90;

	private int mProgressCount = 90;

	private float mInnerRatio = 0.80f;

	private int mWidth = 0;
	private int mHeight = 0;



	/**
	 * it is must be a rang of 0 to 100,means percentage
	 */
	private int mCurentProgress = 100;

	public CircleChart(Context context) {
		super(context);
	}

	public CircleChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(400, 400);
		setMeasuredDimension(mWidth, mHeight);
	}

	private void init(AttributeSet attrs){
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final int height = getMeasuredHeight();
		final int width = getMeasuredWidth();
		final RectF oval = new RectF();
		final RectF ovalColor = new RectF();
		final float centX = width / 2f;
		final float centpY = height / 2f;
		float circleRadius = Math.min(centX,centpY);
		float circleRadiusClr = circleRadius - 2;
		float circleInnerRadius = circleRadius * mInnerRatio;
		final int sipDis = 360 / mProgressCount / 2;
		final int perDis = sipDis * 2;

		float[] positions = null;
		int[] chartColor = (mCharColor == null || mCharColor.length == 0) ? mDefaultColor : mCharColor;


		oval.set(centX - circleRadius, centpY - circleRadius, centX + circleRadius, centpY + circleRadius);
		ovalColor.set(centX - circleRadiusClr, centpY - circleRadiusClr, centX + circleRadiusClr, centpY + circleRadiusClr);

		if (chartColor.length <= 1) {

			int copyColor = chartColor[0];
			chartColor = new int[2];
			chartColor[0] = copyColor;
			chartColor[1] = copyColor;
			positions = new float[]{0f, 1f};

		} else {

			float posDis = 1f / (chartColor.length - 1);
			int posLen = chartColor.length;
			positions = new float[posLen];
			for (int i = 0; i < posLen; ++i) {
				positions[i] = i * posDis;
			}
			positions[posLen - 1] = 1f;

		}

		Paint paint = new Paint();
		Shader shader = new SweepGradient(centX, centpY, chartColor,
				positions);
		float rotate = 90f;
		Matrix gradientMatrix = new Matrix();
		shader.getLocalMatrix(gradientMatrix);
		gradientMatrix.postRotate(rotate, centX, centpY);
		shader.setLocalMatrix(gradientMatrix);
		paint.setShader(shader);

		float destAngle = 360 * mCurentProgress / FULL_PERCENT;

		destAngle += destAngle % sipDis;

		canvas.drawArc(ovalColor, mSartAngle, destAngle, true, paint);

		Paint dipPaint = new Paint();
		dipPaint.setAntiAlias(true);

		for (int i = 0; i < mProgressCount; ++i) {
			float startArg = mSartAngle + sipDis + i * perDis;
			if (startArg < (destAngle + mSartAngle)) {
				dipPaint.setColor(mInnerCircleColor);
				canvas.drawArc(oval, startArg, sipDis, true, dipPaint);
			} else {
				dipPaint.setColor(mSepColor);
				canvas.drawArc(oval, startArg - sipDis, sipDis, true, dipPaint);
			}
		}

		Paint circPaint = new Paint();
		circPaint.setColor(mInnerCircleColor);
		circPaint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(centX,centpY,circleInnerRadius,circPaint);
	}

	public int getCurentProgress() {
		return mCurentProgress;
	}

	public void setCurentProgress(int curentProgress) {
		mCurentProgress = curentProgress;
		invalidate();
	}

	public int[] getCharColor() {
		return mCharColor;
	}

	public void setCharColor(int[] charColor) {
		mCharColor = charColor;
	}

	public int getSepColor() {
		return mSepColor;
	}

	public void setSepColor(int sepColor) {
		mSepColor = sepColor;
	}

	public int getSartAngle() {
		return mSartAngle;
	}

	public void setSartAngle(int sartAngle) {
		mSartAngle = sartAngle;
	}

	public int getProgressCount() {
		return mProgressCount;
	}

	public void setProgressCount(int progressCount) {
		mProgressCount = progressCount;
	}

	public void setSize(int width, int height){
		mHeight = height;
		mWidth = width;
	}
}
