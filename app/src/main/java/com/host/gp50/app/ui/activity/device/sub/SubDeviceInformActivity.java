package com.host.gp50.app.ui.activity.device.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.SwitchView;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device.sub
 *
 * @author Administrator
 * @date 2017/12/25
 */

public class SubDeviceInformActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.zone_alias)
    TextView zoneAlias;
    @BindView(R.id.tv_zone_rank)
    TextView tvZoneRank;
    @BindView(R.id.ll_alias)
    LinearLayout llAlias;
    @BindView(R.id.zone_type)
    TextView zoneType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.sw01)
    SwitchView sw01;
    @BindView(R.id.ll_type01)
    LinearLayout llType01;
    @BindView(R.id.sw02)
    SwitchView sw02;
    @BindView(R.id.ll_type02)
    LinearLayout llType02;
    @BindView(R.id.sw03)
    SwitchView sw03;
    @BindView(R.id.ll_type03)
    LinearLayout llType03;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_device_inform);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);
    }

    @OnClick({R.id.tittleBack, R.id.ll_alias, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.ll_alias:
                //防区别名
                break;
            case R.id.ll_type:
                //防区类型
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
