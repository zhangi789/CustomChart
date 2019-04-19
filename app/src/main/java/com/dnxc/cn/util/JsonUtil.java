package com.dnxc.cn.util;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe
 */
public class JsonUtil {
    public ChartCallBack callBack;

    public void setCallBack(ChartCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 手动解析
     * 需要X轴数据集合
     * Y轴数据集合
     */
    public static void getChartData(String jsonString, ChartCallBack callBack) {
        ArrayList<Entry> mYData = new ArrayList<>();
        ArrayList<String> mXData = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            int errCode = jsonObject.getInt("code");
            int type = jsonObject.getInt("type");
            if (errCode == 0) {
                //颜色集合
                JSONArray rsp = jsonObject.getJSONArray("data");
                for (int i = 0; i < rsp.length(); i++) {
                    String mDes = "";
                    JSONObject rspBean = rsp.getJSONObject(i);
                    String electricity = rspBean.getString("electricity");
                    float yValue = Float.parseFloat(electricity);
                    String xDescription = "";
                    if (type == 1) {
                        mDes = rspBean.getString("date");
                        xDescription = mDes;
                    } else if (type == 2) {
                        mDes = rspBean.getString("date");
                        String[] split = mDes.split("-");
                        xDescription = split[1];
                    } else {
                        mDes = rspBean.getString("date");
                        String[] split = mDes.split("-");
                        xDescription = split[2];
                    }
                    mYData.add(new Entry(i, yValue));
                    mXData.add(xDescription);
                    if (i == (rsp.length() - 1)) {
                        callBack.onRusult(mXData, mYData);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
