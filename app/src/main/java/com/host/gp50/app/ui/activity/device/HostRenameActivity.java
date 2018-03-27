package com.host.gp50.app.ui.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.okgo.callbacklistener.OkGoEditDeviceListener;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.bean.Host;
import com.host.gp50.app.ui.view.ClearEditText;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 * <p>
 * 主机重命名界面
 *
 * @author Administrator
 * @date 2017/12/16
 */

public class HostRenameActivity extends BaseActivity {

    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.etHostAlias)
    ClearEditText etHostAlias;
    @BindView(R.id.bt_save)
    Button btSave;

    private Host host;
    private String hostId;

    private OkGoEditDeviceListener okGoEditDeviceListener = new OkGoEditDeviceListener() {
        @Override
        public void onCallBack(String content) {

            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                onCallBackMessage(getString(R.string.edit_success), Api.SUCCESS);
            } else {
                onCallBackMessage(JsonUtil.getValueByKey(content, "error"), Api.FAIL);
            }
        }

        @Override
        public void onError(String error) {
            onCallBackMessage(error, Api.ERROR);
        }
    };
    private String newAlias;

    @Override
    public void onDialogDismissNext() {
        super.onDialogDismissNext();
        if (isSuccess) {
            Intent intent = new Intent();
            intent.putExtra(Api.RENAME, newAlias);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_rename);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        OkGoManger.getInstance().setOkGoEditDeviceListener(okGoEditDeviceListener);

        Intent intent = getIntent();
        host = (Host) intent.getSerializableExtra(Api.selectHost);
        String alias = host.getAlias();
        hostId = host.getHostId();


        tittleText.setText(alias);
        etHostAlias.setText(alias);
        etHostAlias.setHint(alias);
    }

    @OnClick({R.id.tittleBack, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.bt_save:
                newAlias = etHostAlias.getText().toString().trim();
                if (TextUtils.isEmpty(newAlias)) {
                    break;
                }
                OkGoManger.getInstance().editDevice(spf.getString(UserApi.USER_ID, ""),
                        hostId, newAlias, "", "", "",
                        spf.getString(UserApi.TOKEN, ""));
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
                break;
            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
