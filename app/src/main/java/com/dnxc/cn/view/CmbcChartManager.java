package com.dnxc.cn.view;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe
 */
public class CmbcChartManager {
    private DecimalFormat mDecimalFormat = new DecimalFormat("##,##0.0#");
    private LineChart mLineChart;

    public CmbcChartManager(LineChart lineChart) {
        mLineChart = lineChart;
        initChart(lineChart);
    }

    private void initChart(LineChart lineChart) {
        if (lineChart != null) {
            lineChart.getLegend().setEnabled(false);// 不显示图例
            lineChart.getDescription().setEnabled(false);// 不显示描述
            lineChart.setScaleEnabled(false);   // 取消缩放
            lineChart.setNoDataText("暂无数据");// 没有数据的时候默认显示的文字
            lineChart.setNoDataTextColor(Color.GRAY);
            lineChart.setBorderColor(Color.BLUE);
            lineChart.setTouchEnabled(true);
            lineChart.setDragEnabled(true);
            // 如果x轴label文字比较大，可以设置边距
            lineChart.setExtraRightOffset(25f);
            lineChart.setExtraLeftOffset(25f);
            lineChart.setExtraBottomOffset(10f);
            lineChart.setExtraTopOffset(10f);

            initChartXAxis(lineChart);
            initChartYAxis(lineChart);
        }
    }

    public void setMarkView(CmbcMarkView markerView) {
        if (markerView != null) {
            markerView.setChartView(mLineChart);
            mLineChart.setMarker(markerView);
        }
    }

    public void setData(List<Entry> entries) {
        mLineChart.setData(getLineData(entries));
    }

    /**
     * 初始化Y轴
     *
     * @param lineChart
     */
    private void initChartYAxis(LineChart lineChart) {
        if (lineChart != null) {
            // 不显示右侧Y轴
            YAxis yAxisRight = lineChart.getAxisRight();
            yAxisRight.setEnabled(false);

            YAxis yAxisLeft = lineChart.getAxisLeft();
            // 强制显示Y轴6个坐标
            yAxisLeft.setLabelCount(5, true);
            // 将y轴0点轴的颜色设置为透明
            yAxisLeft.setZeroLineColor(Color.WHITE);
            yAxisLeft.setTextColor(Color.parseColor("#8F8E94"));
            yAxisLeft.setTextSize(10f);
            // 设置y轴网格的颜色
            yAxisLeft.setGridColor(Color.parseColor("#8F8E94"));
            // yAxisLeft.setGranularity(0.5f);
            //   yAxisLeft.setAxisMaximum(0.0f);
            yAxisLeft.setAxisMinimum(0f);
            yAxisLeft.setEnabled(false);
            //Y方向文字的位置，在线外侧.(默认在外侧)
            yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            // 格式化Y轴数据
            yAxisLeft.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float v, AxisBase axisBase) {
                    return mDecimalFormat.format(v);
                }
            });
        }
    }

    /**
     * 初始化X轴
     *
     * @param lineChart
     */
    private void initChartXAxis(LineChart lineChart) {
        if (lineChart != null) {
            // 设置x轴的数据
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.parseColor("#8F8E94"));
            xAxis.setTextSize(10);
            // 设置x轴网格的颜色
            xAxis.setGridColor(Color.parseColor("#8F8E94"));

            xAxis.setGranularity(1.0f);

            xAxis.setDrawGridLines(false);
            //如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
            // xAxis.setAvoidFirstLastClipping(true);
            // x轴最左多出空n个坐标
            // xAxis.setSpaceMax(1.0f);
            // 让左侧x轴不从0点开始
            // xAxis.setSpaceMin(0.1f);
        }
    }

    /**
     * 获取含有数据的LineDataSet对象
     *
     * @param entries
     * @return
     */
    private LineData getLineData(List<Entry> entries) {
        LineDataSet lineDataSet = new LineDataSet(entries, null);
        if (entries != null) {
            // 点击圆点不显示高亮
            lineDataSet.setDrawHighlightIndicators(false);
            // 设置折线的颜色
            lineDataSet.setColor(Color.parseColor("#FC863E"));
            // 填充颜色(渐变色)
            lineDataSet.setDrawFilled(true);
            // 设置平滑曲线模式
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setCircleColor(Color.parseColor("#FC863E"));
            lineDataSet.setCircleRadius(4f);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setCircleHoleColor(Color.parseColor("#FFFFFF"));
            lineDataSet.setCircleHoleRadius(2f);
            lineDataSet.setFillDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{Color.parseColor("#31FF5A00"), Color.parseColor("#00FA5544")
                    }));
            lineDataSet.setLineWidth(1f);
            // 坐标不显示值
            lineDataSet.setDrawValues(false);
        }
        return new LineData(lineDataSet);
    }


}
