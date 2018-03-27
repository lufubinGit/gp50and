package com.host.gp50.app.ui.activity.device;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.okgo.callbacklistener.OkGoBindHostListener;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.ClearEditText;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;
import com.zxing.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.host.gp50.app.utils.ColorUtil.tintDrawable;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/29
 */

public class AddDeviceActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.et_hostID)
    ClearEditText etHostID;
    @BindView(R.id.zxing)
    ImageView zxing;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private OkGoBindHostListener okGoBindHostListener = new OkGoBindHostListener() {
        @Override
        public void onCallBack(String content) {

            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                onCallBackMessage(getString(R.string.add_success), Api.SUCCESS);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);
        OkGoManger.getInstance().setOkGoBindHostListener(okGoBindHostListener);

        final Drawable originalBitmapDrawable = getResources().getDrawable(R.drawable.zxing).mutate();
        zxing.setImageDrawable(tintDrawable(originalBitmapDrawable, ColorStateList.valueOf(getResources().getColor(R.color.theme))));
    }

    @OnClick({R.id.tittleBack, R.id.zxing, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.zxing:
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.putExtra("MODULE", 1);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_add:
                String hostId = etHostID.getText().toString().trim();
                if (TextUtils.isEmpty(hostId)) {
                    ToastUtil.showToast(this, getString(R.string.toast_no_host_id));
                    break;
                }
                OkGoManger.getInstance().bindHost(hostId, spf.getString(UserApi.USER_ID, ""),
                        spf.getString(UserApi.TOKEN, ""));
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String hostId = data.getStringExtra("HostId");
                etHostID.setText(hostId);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
