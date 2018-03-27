package com.host.gp50.app.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.host.gp50.app.MyApplication;
import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.device.sub.SubDeviceInformActivity;
import com.host.gp50.app.ui.bean.Zone;

import java.util.List;

/**
 * com.host.gp50.app.ui.adapter
 *
 * @author Administrator
 * @date 2017/12/18
 */

public class SubDeviceAdapter extends RecyclerView.Adapter<SubDeviceAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Activity activity;
    private List<Zone> zones;


    public SubDeviceAdapter(Activity activity,List<Zone> zones) {
        this.mInflater = LayoutInflater.from(MyApplication.golbalContext);
        this.activity = activity;
        this.zones = zones;
    }

    @Override
    public SubDeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_show_sub_deviece, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubDeviceAdapter.ViewHolder viewHolder, int i) {
        Zone zone = zones.get(i);
        if (zone.isAlarm()) {
            viewHolder.ivEvent.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivEvent.setVisibility(View.GONE);
        }
        String alias = zone.getAlias();
        if (TextUtils.isEmpty(alias)) {
            viewHolder.tvSubDeviceAlias.setText(activity.getString(R.string.zone));
        } else {
            viewHolder.tvSubDeviceAlias.setText(alias);
        }
    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivSubDeviceAlias;
        TextView tvSubDeviceAlias;
        TextView tvSubDeviceType;
        ImageView ivEvent;
        LinearLayout itemSubDevice;

        public ViewHolder(View itemView) {
            super(itemView);
            itemSubDevice = (LinearLayout) itemView.findViewById(R.id.item_sub_device);
            ivSubDeviceAlias = (ImageView) itemView.findViewById(R.id.iv_sub_device_type);
            tvSubDeviceAlias = (TextView) itemView.findViewById(R.id.tv_sub_device_alias);
            tvSubDeviceType = (TextView) itemView.findViewById(R.id.tv_sub_device_type);
            ivEvent = (ImageView) itemView.findViewById(R.id.iv_event);

            itemSubDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, SubDeviceInformActivity.class);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                }
            });
        }

    }
}
