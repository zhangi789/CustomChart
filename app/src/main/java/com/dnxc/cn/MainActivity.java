package com.dnxc.cn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dnxc.cn.ui.A;
import com.dnxc.cn.ui.BankActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_bank)
    Button btnBank;
    @BindView(R.id.btn_ali)
    Button btnAli;
    @BindView(R.id.btn_other)
    Button btnOther;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        ivBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("功能列表");
    }

    @OnClick({R.id.btn_bank, R.id.btn_ali, R.id.btn_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bank:
                startActivity(new Intent(MainActivity.this, BankActivity.class));
                break;
            case R.id.btn_ali:

                startActivity(new Intent(MainActivity.this, A.class));

                break;
            case R.id.btn_other:

                break;
        }
    }
}
