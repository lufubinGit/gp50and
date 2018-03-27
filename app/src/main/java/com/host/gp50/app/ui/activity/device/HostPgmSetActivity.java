package com.host.gp50.app.ui.activity.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.SwitchView;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/15
 */

public class HostPgmSetActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.tv_second)
    EditText tvSecond;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.sw)
    SwitchView sw;
    @BindView(R.id.sw01)
    SwitchView sw01;
    @BindView(R.id.sw02)
    SwitchView sw02;
    @BindView(R.id.sw03)
    SwitchView sw03;
    @BindView(R.id.sw04)
    SwitchView sw04;
    @BindView(R.id.alarm_sw01)
    SwitchView alarmSw01;
    @BindView(R.id.alarm_sw02)
    SwitchView alarmSw02;
    @BindView(R.id.alarm_sw03)
    SwitchView alarmSw03;
    @BindView(R.id.alarm_sw04)
    SwitchView alarmSw04;
    @BindView(R.id.alarm_sw05)
    SwitchView alarmSw05;
    @BindView(R.id.alarm_sw06)
    SwitchView alarmSw06;
    @BindView(R.id.alarm_sw07)
    SwitchView alarmSw07;
    @BindView(R.id.alarm_sw08)
    SwitchView alarmSw08;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private List<SwitchView> switchViewList;
    private List<SwitchView> alarmSwitchViewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_pgm_set);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        initView();
    }

    private void initView() {
        switchViewList = new ArrayList<>();
        switchViewList.add(sw01);
        switchViewList.add(sw02);
        switchViewList.add(sw03);
        switchViewList.add(sw04);

        alarmSwitchViewList = new ArrayList<>();
        alarmSwitchViewList.add(alarmSw01);
        alarmSwitchViewList.add(alarmSw02);
        alarmSwitchViewList.add(alarmSw03);
        alarmSwitchViewList.add(alarmSw04);
        alarmSwitchViewList.add(alarmSw05);
        alarmSwitchViewList.add(alarmSw06);
        alarmSwitchViewList.add(alarmSw07);
        alarmSwitchViewList.add(alarmSw08);

        sw.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                llContent.setVisibility(View.VISIBLE);
                sw.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                llContent.setVisibility(View.GONE);
                sw.toggleSwitch(false);
            }
        });

        sw01.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                closeAllSw();
                closeAllAlarmSw();
                sw01.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                sw01.toggleSwitch(false);
            }
        });

        sw02.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                closeAllSw();
                closeAllAlarmSw();
                sw02.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                sw02.toggleSwitch(false);
            }
        });

        sw03.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                closeAllSw();
                closeAllAlarmSw();
                sw03.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                sw03.toggleSwitch(false);
            }
        });

        sw04.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                closeAllSw();
                closeAllAlarmSw();
                sw04.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                sw04.toggleSwitch(false);
            }
        });

        for (int i = 0; i < alarmSwitchViewList.size(); i++) {
            final SwitchView switchView = alarmSwitchViewList.get(i);
            switchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchView view) {
                    closeAllSw();
                    switchView.toggleSwitch(true);
                }

                @Override
                public void toggleToOff(SwitchView view) {
                    switchView.toggleSwitch(false);
                }
            });
        }
    }

    private void closeAllSw() {
        for (int i = 0; i < switchViewList.size(); i++) {
            SwitchView switchView = switchViewList.get(i);
            switchView.toggleSwitch(false);
        }
    }

    private void closeAllAlarmSw() {
        for (int i = 0; i < alarmSwitchViewList.size(); i++) {
            SwitchView switchView = alarmSwitchViewList.get(i);
            switchView.toggleSwitch(false);
        }
    }

    @OnClick({R.id.tittleBack, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.tv_save:
                //保存
                int newSecond = Integer.parseInt(tvSecond.getText().toString());
                if (newSecond < 0 || newSecond > 300) {
                    ToastUtil.showToast(HostPgmSetActivity.this, "超出范围");
                    break;
                }
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
