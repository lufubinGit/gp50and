package com.host.gp50.app.ui.activity.device.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device.sub
 *
 * @author Administrator
 * @date 2017/12/26
 */

public class SetZoneTypeActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.type01)
    RadioButton type01;
    @BindView(R.id.type02)
    RadioButton type02;
    @BindView(R.id.type03)
    RadioButton type03;
    @BindView(R.id.type04)
    RadioButton type04;
    @BindView(R.id.type05)
    RadioButton type05;
    @BindView(R.id.type06)
    RadioButton type06;
    @BindView(R.id.type07)
    RadioButton type07;
    @BindView(R.id.type08)
    RadioButton type08;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_zone_type);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tittleBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
