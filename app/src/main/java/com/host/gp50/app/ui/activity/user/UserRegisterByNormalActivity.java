package com.host.gp50.app.ui.activity.user;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.okgo.callbacklistener.OkGoRegisterListener;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.ClearEditText;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.user
 * <p>
 * 普通注册界面
 *
 * @author Administrator
 * @date 2017/12/16
 */

public class UserRegisterByNormalActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.etUserName)
    ClearEditText etUserName;
    @BindView(R.id.etPassword)
    ClearEditText etPassword;
    @BindView(R.id.etPassword02)
    ClearEditText etPassword02;
    @BindView(R.id.bt_login)
    Button btLogin;

    private OkGoRegisterListener okGoRegisterListener = new OkGoRegisterListener() {
        @Override
        public void onCallBack(String content) {

            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                onCallBackMessage(getString(R.string.register_success), Api.SUCCESS);
            } else {
                onCallBackMessage(JsonUtil.getValueByKey(content, "error"), Api.FAIL);
            }
        }

        @Override
        public void onError(String error) {
            onCallBackMessage(error, Api.ERROR);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register_normal);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        OkGoManger.getInstance().setOkGoRegisterListener(okGoRegisterListener);
    }

    @Override
    public void handleMessageOnNext(Message msg) {
        super.handleMessageOnNext(msg);

    }

    @Override
    public void onDialogDismissNext() {
        super.onDialogDismissNext();
        if (isSuccess) {
            finish();
        }
    }

    @OnClick({R.id.tittleBack, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.bt_login:
                String userName = etUserName.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                String passWord02 = etPassword02.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord) || TextUtils.isEmpty(passWord02)) {
                    ToastUtil.showToast(this, getString(R.string.toast_no_empty_username_password));
                    break;
                }
                if (!TextUtils.equals(passWord, passWord02)) {
                    ToastUtil.showToast(this, getString(R.string.toast_twice_password_different));
                    break;
                }
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING,getString(R.string.loading));
                OkGoManger.getInstance().registerUser(userName, passWord, "3", "");
                break;
            default:
                break;
        }
    }

    private void registerUser(String userName, String passWord) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
