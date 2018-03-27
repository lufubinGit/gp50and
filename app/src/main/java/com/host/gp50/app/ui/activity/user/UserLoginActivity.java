package com.host.gp50.app.ui.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.okgo.callbacklistener.OkGoLoginListener;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.activity.MainActivity;
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
 * 用户登录界面
 *
 * @author Administrator
 * @date 2017/12/16
 */

public class UserLoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.etUserName)
    ClearEditText etUserName;
    @BindView(R.id.etPassword)
    ClearEditText etPassword;
    @BindView(R.id.iv_password_show)
    ImageView ivPasswordShow;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_losePw)
    TextView tvLosePw;
    @BindView(R.id.tv_register_user)
    TextView tvRegisterUser;
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;

    private boolean isShowPw;
    private Dialog dialog;

    private OkGoLoginListener okGoLoginListener = new OkGoLoginListener() {
        @Override
        public void onCallBack(String content) {

            if (JsonUtil.getBooleanValueByKey(content, "result")) {

                onCallBackMessage(getString(R.string.login_success), Api.SUCCESS);
                String data = JsonUtil.getValueByKey(content, "data");
                String token = JsonUtil.getValueByKey(data, "token");
                String userid = JsonUtil.getValueByKey(data, "userid");
                String nickname = JsonUtil.getValueByKey(data, "nickname");
                String email = JsonUtil.getValueByKey(data, "email");
                String userphonenum = JsonUtil.getValueByKey(data, "userphonenum");
                String userpsw = JsonUtil.getValueByKey(data, "userpsw");
                String avatar = JsonUtil.getValueByKey(data, "avatar");

                spf.putString(UserApi.USER_ID, userid);
                spf.putString(UserApi.USER_NAME, userName);
                spf.putString(UserApi.TOKEN, token);
                spf.putString(UserApi.USER_PW, passWord);
                spf.putString(UserApi.NICKNAME, nickname);

            } else {
                onCallBackMessage(JsonUtil.getValueByKey(content, "error"), Api.FAIL);
            }
        }

        @Override
        public void onError(String error) {
            onCallBackMessage(error, Api.ERROR);
        }
    };
    private String userName;
    private String passWord;

    @Override
    public void handleMessageOnNext(Message msg) {
        super.handleMessageOnNext(msg);
    }

    @Override
    public void onDialogDismissNext() {
        super.onDialogDismissNext();
        if (isSuccess) {
            isSuccess = false;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        OkGoManger.getInstance().setOkGoLoginListener(okGoLoginListener);

        String userName = spf.getString(UserApi.USER_NAME, "");
        String userPw = spf.getString(UserApi.USER_PW, "");
        etUserName.setText(userName);
        etPassword.setText(userPw);
    }

    @OnClick({R.id.iv_password_show, R.id.bt_login, R.id.tv_losePw, R.id.tv_register_user, R.id.tittleBack})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.iv_password_show:
                //显示密码
                if (!isShowPw) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivPasswordShow.setImageResource(R.drawable.password_show);
                    isShowPw = true;
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivPasswordShow.setImageResource(R.drawable.password_close);
                    isShowPw = false;
                }
                etPassword.setSelection(etPassword.getText().length());
                break;
            case R.id.bt_login:
                //登录
                userName = etUserName.getText().toString().trim();
                passWord = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
                    ToastUtil.showToast(this, getString(R.string.toast_no_empty_username_password));
                    break;
                }
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
                OkGoManger.getInstance().userLogin(userName, passWord, "1");
                break;
            case R.id.tv_losePw:
                //忘记密码
                break;
            case R.id.tv_register_user:
                //注册用户
                showMenu();
                break;
            default:
                break;
        }
    }

    /**
     * 显示dialog
     */
    private void showMenu() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_register_user, null);
        TextView tvByNormal = (TextView) inflate.findViewById(R.id.tv_by_normal);
        TextView tvByPhone = (TextView) inflate.findViewById(R.id.tv_by_phone);
        TextView tvByEmail = (TextView) inflate.findViewById(R.id.tv_by_email);
        TextView cancel = (TextView) inflate.findViewById(R.id.cancel);

        //初始化控件
        tvByNormal.setOnClickListener(this);
        tvByPhone.setOnClickListener(this);
        tvByEmail.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出

        //获得窗体的属性
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.y = 20;
        //将属性设置给窗体
        dialogWindow.setAttributes(params);
        dialog.show();//显示对话框
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_by_normal:
                //普通注册
                dialog.dismiss();
                intent = new Intent(this, UserRegisterByNormalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_by_phone:
                //电话注册
                dialog.dismiss();
//                intent = new Intent(this, UserRegisterByPhoneActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_by_email:
                //邮箱注册
                dialog.dismiss();
//                intent = new Intent(this, UserRegisterByEmailActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.cancel:
                dialog.dismiss();
            default:
                break;
        }
    }

}
