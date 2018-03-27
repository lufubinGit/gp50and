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
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/08
 */

public class SetPhoneActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.sms_name)
    EditText smsName;
    @BindView(R.id.sms_number)
    EditText smsNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);
    }

    @OnClick({R.id.tittleBack, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                break;
            case R.id.tv_save:
                break;
            default:
                break;
        }
    }
}
