package com.dnxc.cn.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dnxc.cn.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe 仿招商银行 自定义MarkView
 * CMBC    China Merchants Bank of China
 */
public class CmbcMarkView extends MarkerView {
    private final TextView mTvDate;
    private final TextView mTvMoney;
    private final LinearLayout mLlTopArrow;
    private final LinearLayout mLlBottomArrow;
    private final ImageView mIvDot;
    private List<String> mXAxisLabels;
    private MPPointF mMPPointF = new MPPointF();
    private float mHighlightXValue;
    private float mHighlightYValue;

    public CmbcMarkView(Context context, int layoutResource) {
        super(context, layoutResource);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvMoney = (TextView) findViewById(R.id.tv_money);
        mLlTopArrow = (LinearLayout) findViewById(R.id.ll_move_top);
        mLlBottomArrow = (LinearLayout) findViewById(R.id.ll_move_bottom);
        mIvDot = (ImageView) findViewById(R.id.iv_bottom_dot);
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        if (mXAxisLabels == null) {
            throw new NullPointerException("mXAxisLabels 不能为空，请调用setXAxisLabels()为其设置数据");
        }
        if (e != null) {
            float x = e.getX();
            float y = e.getY();
            Log.i("GGG","---"+y);

           // String.format("%s 收益", mXAxisLabels.get((int) x));
            mTvMoney.setText(""+mXAxisLabels.get((int) x));
            mTvDate.setText(""+y);
        }
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        int widthHalf = getWidth() / 2;
        mMPPointF.x = 0;
        // 设置x轴MarkView的移动
        int right = getChartView().getWidth();
        if (posX < widthHalf) {
            // 在最左侧的情况
            mMPPointF.x = -posX;
        } else if ((posX >= widthHalf) && (posX + widthHalf) <= right) {
            mMPPointF.x = -widthHalf;
        } else if ((posX + widthHalf) > right) {
            // 在最右侧的情况
            float dis = getWidth() - (right - posX);
            mMPPointF.x = -dis;
        }
        moveArrow(Math.abs(mMPPointF.x));

        // 设置y轴MarkView的移动 如果上面可以放下mark就在上面显示，否则在下面显示
        if (posY > (getRootView().getHeight() + mIvDot.getHeight())) {
            showTop(mMPPointF);
        } else {
            showBottom(mMPPointF);
        }
        // 这里必须要在重新测量、摆放,不然会使用上次的位置
        refreshContent(null, null);
        return mMPPointF;
    }

    /**
     * mark在点的下方
     *
     * @param mpPointF
     */
    private void showBottom(MPPointF mpPointF) {
        if (mpPointF != null) {
            mpPointF.y = -mIvDot.getHeight() / 2;
            mLlBottomArrow.setVisibility(GONE);
            mLlTopArrow.setVisibility(VISIBLE);
        }
    }

    private void showTop(MPPointF mpPointF) {
        if (mpPointF != null) {
            mpPointF.y = -(getHeight() - mIvDot.getHeight() / 2);
            mLlBottomArrow.setVisibility(VISIBLE);
            mLlTopArrow.setVisibility(GONE);
        }
    }

    private void moveArrow(float left) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int) (left - mLlTopArrow.getWidth() / 2);
        mLlBottomArrow.setLayoutParams(params);
        mLlTopArrow.setLayoutParams(params);
    }

    public float getHighlightXValue() {
        return mHighlightXValue;
    }

    public float getHighlightYValue() {
        return mHighlightYValue;
    }

    /**
     * 显示MarkView，设置坐标点为高亮即可显示MarkView.如果LineData的构造参数传入的对象为
     *
     * @param xValue 点X坐标坐标对应的值
     * @param yValue 点Y坐标坐标对应的值
     */
    public void showMarkView(float xValue, float yValue) {
        showMarkView(xValue, yValue, 0);
    }

    /**
     * 显示MarkView，设置坐标点为高亮即可显示MarkView
     *
     * @param xValue       点X坐标坐标对应的值
     * @param yValue       点Y坐标坐标对应的值
     * @param dataSetIndex 设置集合的索引值，根据LineData构造中传入的对象来确定值：1、如果LineData()构造中传入的是LineDataSet对象，设置dataSetIndex为0，
     *                     2、如果传入的是List集合对象，则dataSetIndex的值为该元素对应集合的索引值
     */
    public void showMarkView(float xValue, float yValue, int dataSetIndex) {
        mHighlightXValue = xValue;
        mHighlightYValue = yValue;
        Chart chartView = getChartView();
        if (chartView != null && chartView.getData() != null) {
            chartView.highlightValue(xValue, yValue, 0);
        }
    }
    /**
     * 设置 X 轴label的集合
     */
    public void setXAxisLabels(List<String> xAxisLabels) {
        mXAxisLabels = xAxisLabels;
    }
}
