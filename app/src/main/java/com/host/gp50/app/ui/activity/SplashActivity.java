package com.host.gp50.app.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.user.UserLoginActivity;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.runtimepermissions.PermissionsManager;
import com.host.gp50.app.utils.runtimepermissions.PermissionsResultAction;

/**
 * com.host.gp50.app.ui.activity
 *
 * @author Administrator
 * @date 2017/11/28
 */

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        /*
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        StatusbarUtils.initAfterSetContentView(this, null);

        handler.sendEmptyMessageDelayed(0, 2500);
    }
}
