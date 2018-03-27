package com.host.gp50.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.fragment.DeviceFragment;
import com.host.gp50.app.ui.fragment.UserFragment;
import com.host.gp50.app.ui.service.MySocketService;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * com.host.gp50.app.ui.activity
 *
 * @author Administrator
 * @date 2017/11/28           01234561234
 */
public class MainActivity extends BaseActivity {
    private int SHARE_HOST_CODE = 2;
    private int[] icons = new int[]{R.drawable.radio_device, R.drawable.radio_user};
    private int[] strings = new int[]{R.string.device, R.string.user};
    private Class[] fragments = new Class[]{DeviceFragment.class, UserFragment.class};

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    boolean isShare;
    private String subUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        tabhost.setup(this, getSupportFragmentManager(), R.id.fl_container);

        for (int i = 0; i < icons.length; i++) {
            View view = View.inflate(this, R.layout.tab_background, null);
            ImageView ivICon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tvDesc = (TextView) view.findViewById(R.id.tv_desc);
            tvDesc.setText(getString(strings[i]));
            ivICon.setBackgroundResource(icons[i]);
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(fragments[i].getSimpleName()).setIndicator(view);
            tabhost.addTab(tabSpec, fragments[i], null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MySocketService.class);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult","onActivityResult:" + resultCode);
        if (resultCode == RESULT_OK) {
            Log.i("onActivityResult","onActivityResult:" + requestCode);
            if (requestCode == SHARE_HOST_CODE) {
                isShare = true;
                subUserId = data.getStringExtra("subUserId");
            }
        }
    }

    private long mExitTime;
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                ToastUtil.showToast(MainActivity.this, getString(R.string.toast_sure_back));
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
//                System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }

    public String getShareUserId() {
        return subUserId;
    }

    public void setShareUserId(String shareUserId) {
        this.subUserId = shareUserId;
    }
}
