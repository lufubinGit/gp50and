package com.host.gp50.app.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.host.gp50.app.Api;
import com.host.gp50.app.MyApplication;
import com.host.gp50.app.R;
import com.host.gp50.app.greendao.gen.DaoSession;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.SharedPreferencesHelper;
import com.host.gp50.app.utils.ToastUtil;
import com.host.gp50.app.utils.runtimepermissions.PermissionsManager;
import com.host.gp50.app.utils.runtimepermissions.PermissionsResultAction;

import java.util.List;

/**
 * com.host.gp50.app.ui.activity
 *
 * @author Administrator
 * @date 2017/11/28
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 存储器
     */
    public SharedPreferencesHelper spf;

    /**
     * 手机屏幕的宽
     */
    public int screenWidth;
    /**
     * 手机屏幕的高
     */
    public int screenHeight;
    public ScaleAnimation viewClickAnimation;
    public QMUITipDialog tipDialog;

    public final int Cancel_Dialog = 99;

    public boolean isSuccess;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String obj = (String) msg.obj;
            switch (msg.what) {
                case Api.FAIL:
                    tipDialog.dismiss();
                    showTipDialog(QMUITipDialog.Builder.ICON_TYPE_FAIL, obj);
                    handler.sendEmptyMessageDelayed(Cancel_Dialog, 1500);
                    break;
                case Api.SUCCESS:
                    tipDialog.dismiss();
                    isSuccess = true;
                    showTipDialog(QMUITipDialog.Builder.ICON_TYPE_SUCCESS, obj);
                    handler.sendEmptyMessageDelayed(Cancel_Dialog, 1000);
                    break;
                case Api.ERROR:
                    tipDialog.dismiss();
                    showTipDialog(QMUITipDialog.Builder.ICON_TYPE_FAIL, getString(R.string.fail));
                    handler.sendEmptyMessageDelayed(Cancel_Dialog, 1500);
                    ToastUtil.showToast(BaseActivity.this, (String) msg.obj);
                    break;
                case Cancel_Dialog:
                    if (tipDialog != null) {
                        tipDialog.dismiss();
                    }
                    onDialogDismissNext();
                    break;
                default:
                    break;
            }
            handleMessageOnNext(msg);
        }
    };

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

    /**
     * 成功弹窗信息显示后的操作
     */
    public void onDialogDismissNext() {

    }

    public DaoSession daoSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        /*
         * 请求所有必要的权限----
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });

        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        spf = SharedPreferencesHelper.getInstance(getApplicationContext());
        MyApplication instances = MyApplication.getInstances();
        daoSession = instances.getDaoSession();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        OkGoManger.getInstance().cancel();
    }

    public void handleMessageOnNext(Message msg) {

    }

    public void showTipDialog(int i, String content) {
        switch (i) {
            case QMUITipDialog.Builder.ICON_TYPE_LOADING:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord(content)
                        .create();
                break;
            case QMUITipDialog.Builder.ICON_TYPE_SUCCESS:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                        .setTipWord(content)
                        .create();
                break;
            case QMUITipDialog.Builder.ICON_TYPE_FAIL:
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                        .setTipWord(content)
                        .create();
                break;
            default:
                break;
        }
        tipDialog.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    public void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public String serviceName = "com.host.gp50.app.ui.service.MySocketService";

    public boolean isServiceWorked(Context context, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public void initViewClickAnimation() {
        viewClickAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        viewClickAnimation.setDuration(100);
    }

}
