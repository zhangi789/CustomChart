package com.dnxc.cn.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.dnxc.cn.view.CmbcChartManager;
import com.dnxc.cn.view.CmbcMarkView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe
 */
public class BaseUtil {
    /**
     * 从Assets 获得Json 字符串
     * 得到json文件中的内容
     *
     * @param context
     * @param fileName "year.json"
     * @return
     */
    public static String getAssetsJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 更新招商银行图表更新
     *
     * @param mBankChart
     * @param mCmbcChartManager
     * @param mMarkerView
     * @param mXData
     * @param mYData
     */
    public static void upDataCmbcChart(LineChart mBankChart, CmbcChartManager mCmbcChartManager,
                                       CmbcMarkView mMarkerView, final ArrayList<String> mXData, ArrayList<Entry> mYData) {
        XAxis xAxis = mBankChart.getXAxis();
        xAxis.setLabelCount(mXData.size());
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                int i = (int) v;
                // 只显示首尾日期
                if (i == 0 || i == mXData.size() - 1) {
                    return mXData.get(i);
                } else {
                    return "";
                }
            }
        });

        // 设置lineChart的数据
        mCmbcChartManager.setData(mYData);
        // 给y轴设置最大值
        float yMax = mBankChart.getYMax();
        mBankChart.getAxisLeft().setAxisMaximum(yMax + 2.0f);
        // 设置X轴的label的集合
        mMarkerView.setXAxisLabels(mXData);
        // 指定最后一个点为高亮，让其自定显示MarkView
        Entry entry = mYData.get(mXData.size() - 1);
        mMarkerView.showMarkView(entry.getX(), entry.getY());
        mBankChart.animateXY(1000, 1000);
        mBankChart.invalidate();
    }


}
