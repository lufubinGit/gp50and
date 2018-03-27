package com.host.gp50.app.ui.activity.device.sub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.adapter.SubDeviceAdapter;
import com.host.gp50.app.ui.bean.Zone;
import com.host.gp50.app.utils.StatusbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device.sub
 *
 * @author Administrator
 * @date 2017/12/18
 */

public class ShowSubDeviceActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.rlv_alarm)
    RecyclerView rlvAlarm;
    @BindView(R.id.rlv_normal)
    RecyclerView rlvNormal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub_device);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this,tittleView);

        initView();
        setData();
    }

    private void initView() {

    }

    private void setData() {
        List<Zone> normalZones = new ArrayList<>();
        List<Zone> alarmZones = new ArrayList<>();

        Zone zone = new Zone();
        zone.setAlias("normalZones");
        zone.setAlarm(false);
        normalZones.add(zone);

        Zone zone2 = new Zone();
        zone2.setAlias("alarmZones");
        zone2.setAlarm(true);
        alarmZones.add(zone2);

        LinearLayoutManager alarmLinearLayoutManager = new LinearLayoutManager(this);
        rlvAlarm.setLayoutManager(alarmLinearLayoutManager);
        LinearLayoutManager normalLinearLayoutManager = new LinearLayoutManager(this);
        rlvNormal.setLayoutManager(normalLinearLayoutManager);

        SubDeviceAdapter alarmAdapter = new SubDeviceAdapter(this, alarmZones);
        SubDeviceAdapter normalAdapter = new SubDeviceAdapter(this, normalZones);
        rlvAlarm.setAdapter(alarmAdapter);
        rlvNormal.setAdapter(normalAdapter);

        if (alarmZones.size() == 0) {
            rlvAlarm.setVisibility(View.GONE);
        } else {
            rlvAlarm.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tittleBack, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.iv_add:
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
