package com.host.gp50.app.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.ClearEditText;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.user
 * <p>
 * 电话注册界面
 *
 * @author Administrator
 * @date 2017/12/16
 */

public class UserRegisterByPhoneActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.etUserName)
    ClearEditText etUserName;
    @BindView(R.id.etPassword)
    ClearEditText etPassword;
    @BindView(R.id.etPassword02)
    ClearEditText etPassword02;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_verify_code)
    TextView tvGetVerifyCode;
    @BindView(R.id.bt_login)
    Button btLogin;
    private int secondleft;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_phone);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tittleBack, R.id.tv_get_verify_code, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.tv_get_verify_code:
                //获取验证码
                isStartTimer();
                break;
            case R.id.bt_login:
                //注册
                break;
            default:
                break;
        }
    }

    @Override
    public void handleMessageOnNext(Message msg) {
        super.handleMessageOnNext(msg);
        secondleft--;
        if (secondleft <= 0) {
            tvGetVerifyCode.setEnabled(true);
            tvGetVerifyCode.setText(getString(R.string.user_get_verify_code));
        } else {
            tvGetVerifyCode.setText(secondleft + "s");
        }

    }

    /**
     * 倒计时
     */
    public void isStartTimer() {
        tvGetVerifyCode.setEnabled(false);
        secondleft = 60;
        int width = tvGetVerifyCode.getWidth();
        tvGetVerifyCode.setText(secondleft + "s");
        tvGetVerifyCode.setWidth(width);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 1000, 1000);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
