package com.azalea.www.hotchart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Panoo on 2015/12/30.
 */
public class HotChartView extends RelativeLayout {

	private static final int DEF_BACK_DRAWABLE = R.drawable.chart_back;
	private static final float CHART_SHRINK_RATIO = 0.8f;


	private static final int DRAWABLE_LOW_LEVEL = R.drawable.green_hot;
	private static final int DRAWABLE_MID_LEVEL = R.drawable.yellow_hot;
	private static final int DRAWABLE_HIGH_LEVEL = R.drawable.red_hot;

	private static final int HOT_LOW_LEVEL = 30;
	private static final int HOT_MID_LEVEL = 60;

	private int mCurrentProgress = 0;

	private CircleChart mChart = null;
	private ImageView mHotLevelImage = null;
	private TextView mTextProgress = null;

	public HotChartView(Context context) {
		super(context);
		init(context, null);
	}

	public HotChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public HotChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		View rootView = LayoutInflater.from(context).inflate(R.layout.layout_hot_chart, this, true);
		mChart = (CircleChart) rootView.findViewById(R.id.circle_chart);
		mHotLevelImage = (ImageView) rootView.findViewById(R.id.img_hot_levle);
		mTextProgress = (TextView) rootView.findViewById(R.id.txt_progress);

		ImageView backImage = (ImageView) rootView.findViewById(R.id.img_back);

		Drawable backDrawable = getResources().getDrawable(DEF_BACK_DRAWABLE);
		int width = backDrawable.getIntrinsicWidth();
		int height = backDrawable.getIntrinsicHeight();
		backImage.setBackgroundDrawable(backDrawable);

		mChart.setSize((int) (width * CHART_SHRINK_RATIO), (int) (height * CHART_SHRINK_RATIO));

		setCurrentProgress(mCurrentProgress);
	}

	public void setCurrentProgress(int progress){
		mCurrentProgress = progress;
		mChart.setCurentProgress(mCurrentProgress);
		processHotLevel(progress);
		mTextProgress.setText(progress + "%");
	}

	private void processHotLevel(int process) {

		if (process <= HOT_LOW_LEVEL) {//低热力

			mHotLevelImage.setBackgroundResource(DRAWABLE_LOW_LEVEL);

		} else if (process <= HOT_MID_LEVEL) {//中热力

			mHotLevelImage.setBackgroundResource(DRAWABLE_MID_LEVEL);

		} else {//高热力

			mHotLevelImage.setBackgroundResource(DRAWABLE_HIGH_LEVEL);

		}
	}

}
