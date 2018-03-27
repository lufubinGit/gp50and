package com.host.gp50.app.ui.activity.others;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.utils.DensityUtil;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.others
 *
 * @author Administrator
 * @date 2017/12/25
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_privacy)
    LinearLayout llPrivacy;
    @BindView(R.id.ll_check_version)
    LinearLayout llCheckVersion;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_app)
    ImageView ivApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        tvVersion.setText(getVersion());
        DensityUtil.setViewLayoutParams(rlBack, (int) (screenWidth * 0.6), LinearLayout.LayoutParams.MATCH_PARENT);
        DensityUtil.setViewLayoutParams(ivApp, (int) (screenWidth * 0.25), (int) (screenWidth * 0.25));
    }


    @OnClick({R.id.tittleBack, R.id.ll_privacy, R.id.ll_check_version})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.ll_privacy:
                intent = new Intent(this, PrivacyPolicyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_check_version:
                break;
            default:
                break;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getApplication().getPackageName(), 0);
            return this.getString(R.string.version) + " " + info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
