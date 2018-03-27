package com.host.gp50.app.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.host.gp50.app.MyApplication;
import com.host.gp50.app.greendao.gen.DaoSession;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.activity.MainActivity;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.SharedPreferencesHelper;

/**
 * com.host.gp50.app.ui.fragment
 *
 * @author Administrator
 * @date 2017/11/28
 */

public abstract class BaseFragment extends Fragment {
    private View rootView;
    public MainActivity activity;
    public DaoSession daoSession;
    public SharedPreferencesHelper spf;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleMessageOnNext(msg);
        }
    };
    public QMUITipDialog tipDialog;

    /**
     * handle
     * @param msg 消息
     */
    protected abstract void handleMessageOnNext(Message msg);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        MyApplication instances = MyApplication.getInstances();
        daoSession = instances.getDaoSession();
        spf = SharedPreferencesHelper.getInstance(getActivity().getApplicationContext());
        //获取屏幕信息
        if (rootView == null) {
            rootView = initView(inflater);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        OkGoManger.getInstance().cancel();
    }

    public void showTipDialog(int i, String content) {
        switch (i) {
            case QMUITipDialog.Builder.ICON_TYPE_LOADING:
                tipDialog = new QMUITipDialog.Builder(activity)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord(content)
                        .create();
                break;
            case QMUITipDialog.Builder.ICON_TYPE_SUCCESS:
                tipDialog = new QMUITipDialog.Builder(activity)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord(content)
                        .create();
                break;
            case QMUITipDialog.Builder.ICON_TYPE_FAIL:
                tipDialog = new QMUITipDialog.Builder(activity)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                        .setTipWord(content)
                        .create();
                break;
            default:
                break;
        }
        tipDialog.show();
    }

    /**
     * 初始化View
     * @param inflater  填充布局
     * @return  布局
     */
    protected abstract View initView(LayoutInflater inflater);

    /**
     * 设置回调后弹窗的信息
     * @param obj   显示字符内容
     * @param what  成功、失败、错误
     */
    public void onCallBackMessage(String obj, int what) {
        Message message = new Message();
        message.obj = obj;
        message.what = what;
        handler.sendMessageDelayed(message, 1500);
    }
}
