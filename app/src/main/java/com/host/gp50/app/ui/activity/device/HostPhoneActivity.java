package com.host.gp50.app.ui.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.greendao.gen.PhoneDao;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.bean.Host;
import com.host.gp50.app.ui.bean.Phone;
import com.host.gp50.app.utils.StatusbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2018/01/04
 */

public class HostPhoneActivity extends BaseActivity {
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.lv_phone)
    ListView lvPhone;

    private List<Phone> phoneList = new ArrayList<>();
    private Host host;
    private PhoneDao phoneDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_phone);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        Intent intent = getIntent();
        host = (Host) intent.getSerializableExtra(Api.selectHost);
        phoneDao = daoSession.getPhoneDao();

        initData();
    }

    private void initData() {
        List<Phone> daoList = phoneDao.queryBuilder().where(PhoneDao.Properties.HostId.eq(host.getHostId())).list();
        for (int i = 0; i < daoList.size(); i++) {
            Phone phone = daoList.get(i);
            phoneList.add(phone);
        }

        PhoneAdapter adapter = new PhoneAdapter();
        lvPhone.setAdapter(adapter);

        lvPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @OnClick(R.id.tittleBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }


    private class PhoneAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return phoneList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PhoneHolder holder;
            if (view == null) {
                view = View.inflate(HostPhoneActivity.this, R.layout.item_phone, null);
                holder = new PhoneHolder();
                holder.alias = (TextView) view.findViewById(R.id.item_tv_name);
                holder.number = (TextView) view.findViewById(R.id.item_tv_number);
                view.setTag(holder);
            } else {
                holder = (PhoneHolder) view.getTag();
            }
            Phone phone = phoneList.get(i);
//            holder.alias.setText();

            return view;
        }
    }

    private class PhoneHolder {
        TextView alias;
        TextView number;
    }
}
