package com.host.gp50.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.activity.others.AboutActivity;
import com.host.gp50.app.ui.activity.user.UserCenterActivity;
import com.host.gp50.app.ui.activity.user.UserLoginActivity;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * com.host.gp50.app.ui.fragment
 *
 * @author Administrator
 * @date 2017/11/28
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.tv_userAlias)
    TextView tvUserAlias;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.ll_user_inform)
    LinearLayout llUserInform;
    @BindView(R.id.ll_set)
    LinearLayout llSet;
    @BindView(R.id.ll_notice)
    LinearLayout llNotice;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @Override
    protected void handleMessageOnNext(Message msg) {

    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        StatusbarUtils.initAfterSetContentView(getActivity(), tittleView);

        setData();

        return view;
    }

    private void setData() {
        tvUserAlias.setText(activity.getString(R.string.user_alias) + spf.getString(UserApi.NICKNAME,""));
        tvUserName.setText(activity.getString(R.string.user_name) + spf.getString(UserApi.USER_NAME,""));
    }

    @OnClick({R.id.ll_user_inform, R.id.ll_set, R.id.ll_notice, R.id.ll_about})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_user_inform:
                //用户信息
                intent = new Intent(activity, UserCenterActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_set:
                //设置
                String token = spf.getString(UserApi.TOKEN, "");
                String userid = spf.getString(UserApi.USER_ID, "");
//                OkGoManger.getInstance().userLogOut(userid, token);
//                OkGoManger.getInstance().queryDeviceSubUser("01001000105000178954",token);
                OkGoManger.getInstance().unbindDevice(userid,"01001000105000178954",token);
//                OkGoManger.getInstance().editUserInfo(userid,spf.getString(UserApi.USER_NAME,""),
//                        "test","123456789","666666",token);
                break;
            case R.id.ll_notice:
                //通知
                intent = new Intent(activity, UserLoginActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_about:
                //关于
                intent = new Intent(activity, AboutActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            default:
                break;
        }
    }
}
