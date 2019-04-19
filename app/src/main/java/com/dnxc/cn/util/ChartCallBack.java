package com.dnxc.cn.util;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * @author 张海洋
 * @Date on 2019/03/04.
 * @org 上海..科技有限公司
 * @describe 用于Json 解析 接口回调
 */
public interface ChartCallBack {
    void onRusult(ArrayList<String> mXData, ArrayList<Entry> data);
}
