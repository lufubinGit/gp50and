package com.host.gp50.app.ui.activity.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/09
 */

public class SetWeekActivity extends BaseActivity {

    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.cb01)
    CheckBox cb01;
    @BindView(R.id.cb02)
    CheckBox cb02;
    @BindView(R.id.cb03)
    CheckBox cb03;
    @BindView(R.id.cb04)
    CheckBox cb04;
    @BindView(R.id.cb05)
    CheckBox cb05;
    @BindView(R.id.cb06)
    CheckBox cb06;
    @BindView(R.id.cb07)
    CheckBox cb07;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_week);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);
    }

    @OnClick(R.id.tittleBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
