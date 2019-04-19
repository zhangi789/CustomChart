package com.dnxc.cn.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnxc.cn.R;
import com.dnxc.cn.util.BaseUtil;
import com.dnxc.cn.util.ChartCallBack;
import com.dnxc.cn.util.JsonUtil;
import com.dnxc.cn.view.CmbcChartManager;
import com.dnxc.cn.view.CmbcMarkView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe 布局可以自定义哦   尽量数据长度唯一
 */
public class BankActivity extends AppCompatActivity {

    @BindView(R.id.mtab)
    TabLayout mtab;
    @BindView(R.id.mBankChart)
    LineChart mBankChart;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    int mTabIndex = 1;
    CmbcChartManager mCmbcChartManager;
    CmbcMarkView mMarkerView;
    ArrayList<String> mXData;
    ArrayList<Entry> mYData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        ButterKnife.bind(this);
        mContext = this;
        initChart();
        tvTitle.setText("仿招商银行图表");

        setTab();
        JsonUtil.getChartData(BaseUtil.getAssetsJson(mContext, "year.json"), new ChartCallBack() {
            @Override
            public void onRusult(ArrayList<String> mXdata, ArrayList<Entry> data) {
                mXData = mXdata;
                mYData = data;
            }
        });
        BaseUtil.upDataCmbcChart(mBankChart,mCmbcChartManager,mMarkerView,mXData,mYData);

    }
    private void initChart() {
        mCmbcChartManager = new CmbcChartManager(mBankChart);
        mMarkerView = new CmbcMarkView(this, R.layout.cmbc_marker_view);
        mCmbcChartManager.setMarkView(mMarkerView);
        mBankChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            private Highlight mHighlight;

            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                mHighlight = highlight;
            }

            @Override
            public void onNothingSelected() {

                if (mHighlight == null) {
                    mHighlight = new Highlight(mMarkerView.getHighlightXValue(), mMarkerView.getHighlightYValue(), 1);
                }
                mMarkerView.showMarkView(mHighlight.getX(), mHighlight.getY());
            }
        });
    }


    private void setTab() {
        mtab.addTab(mtab.newTab().setTag("1").setText("年"));
        mtab.addTab(mtab.newTab().setTag("2").setText("月"));
        mtab.addTab(mtab.newTab().setTag("3").setText("日"));
        mtab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabIndex = Integer.parseInt((String) tab.getTag());

                switch (mTabIndex) {
                    case 1:
                        JsonUtil.getChartData(BaseUtil.getAssetsJson(mContext, "year.json"), new ChartCallBack() {
                            @Override
                            public void onRusult(ArrayList<String> mXdata, ArrayList<Entry> data) {
                                mXData = mXdata;
                                mYData = data;
                            }
                        });
                        BaseUtil.upDataCmbcChart(mBankChart,mCmbcChartManager,mMarkerView,mXData,mYData);
                        break;
                    case 2:
                        JsonUtil.getChartData(BaseUtil.getAssetsJson(mContext, "month.json"), new ChartCallBack() {
                            @Override
                            public void onRusult(ArrayList<String> mXdata, ArrayList<Entry> data) {
                                mXData = mXdata;
                                mYData = data;
                            }
                        });
                        BaseUtil.upDataCmbcChart(mBankChart,mCmbcChartManager,mMarkerView,mXData,mYData);

                        break;
                    case 3:
                        JsonUtil.getChartData(BaseUtil.getAssetsJson(mContext, "day.json"), new ChartCallBack() {
                            @Override
                            public void onRusult(ArrayList<String> mXdata, ArrayList<Entry> data) {
                                mXData = mXdata;
                                mYData = data;
                            }
                        });
                        BaseUtil.upDataCmbcChart(mBankChart,mCmbcChartManager,mMarkerView,mXData,mYData);

                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.isSelected()) {

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        BankActivity.this.finish();
    }
}
