package com.host.gp50.app.ui.activity.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.bean.Auto;
import com.host.gp50.app.utils.StatusbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/04
 */

public class HostTimingPlanActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.lv)
    ListView lv;

    private List<Auto> autoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_plan);
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
