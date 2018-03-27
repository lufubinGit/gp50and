package com.host.gp50.app.ui.activity.device;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.CircleImageView;
import com.host.gp50.app.utils.DensityUtil;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2017/12/20
 */

public class HostServiceProviderActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.civ_service)
    CircleImageView civService;
    @BindView(R.id.tv_service_name)
    TextView tvServiceName;
    @BindView(R.id.rl_service_back)
    RelativeLayout rlServiceBack;
    @BindView(R.id.tv_user_alias)
    TextView tvUserAlias;
    @BindView(R.id.ll_maintain_location)
    LinearLayout llMaintainLocation;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.ll_contact_sales)
    LinearLayout llContactSales;
    @BindView(R.id.ll_feed_back)
    LinearLayout llFeedBack;
    @BindView(R.id.tv_change_service_provider)
    TextView tvChangeServiceProvider;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_service_provider);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this,tittleView);

        initView();
    }

    private void initView() {
        DensityUtil.setViewLayoutParams(rlServiceBack, (int) (screenWidth * 0.6), LinearLayout.LayoutParams.MATCH_PARENT);
        DensityUtil.setViewLayoutParams(civService, (int) (screenWidth * 0.25), (int) (screenWidth * 0.25));

    }

    @OnClick({R.id.tittleBack, R.id.ll_maintain_location, R.id.ll_contact_sales, R.id.ll_feed_back, R.id.tv_change_service_provider})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.ll_maintain_location:
                //最近维护地点
                break;
            case R.id.ll_contact_sales:
                //联系售后
                showCallDialog();
                break;
            case R.id.ll_feed_back:
                //反馈
                break;
            case R.id.tv_change_service_provider:
                //切换服务商
                break;
            default:
                break;
        }
    }

    private void showCallDialog() {
        dialog = new Dialog(this, R.style.CenterDialogStyle);

        View layout = View.inflate(this, R.layout.dialog_call_phone, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        TextView call = (TextView) layout.findViewById(R.id.call);
        TextView tvPhoneNumber = (TextView) layout.findViewById(R.id.tv_phone_number);

        cancel.setOnClickListener(this);
        call.setOnClickListener(this);

        dialog.setContentView(layout);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = screenWidth * 3 / 5;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.call:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }
}
