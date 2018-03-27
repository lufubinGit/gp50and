package com.host.gp50.app.ui.activity.device;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.XListView.XListView;
import com.host.gp50.app.utils.StatusbarUtils;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/23
 */

public class HistoryActivity extends BaseActivity implements XListView.IXListViewListener {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.xlv)
    XListView xlv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);
        xlv.setRefreshTime(getCurrentTime());
    }

    /**
     * 获得系统的最新时间
     *
     * @return
     */
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    @OnClick(R.id.tittleBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void handleMessageOnNext(Message msg) {
        super.handleMessageOnNext(msg);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in,R.anim.anim_back_out);
    }
}
