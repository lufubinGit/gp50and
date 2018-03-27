package com.host.gp50.app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.host.gp50.app.R;
import com.host.gp50.app.ui.bean.Host;

import java.util.List;

/**
 * com.host.gp50.app.ui.adapter
 *
 * @author Administrator
 * @date 2017/12/06
 */

public class PopWindowAdapter extends BaseAdapter {

    private List<Host> hosts;
    private Activity activity;

    public PopWindowAdapter(Activity activity,List<Host> hosts) {
        this.activity = activity;
        this.hosts = hosts;
    }

    @Override
    public int getCount() {
        return hosts.size();
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
        Holder holder;
        if (view == null) {
            view = View.inflate(activity, R.layout.item_pop_menu, null);
            holder = new Holder();
            holder.tvHostName = (TextView) view.findViewById(R.id.tv_host_name);
            holder.tvHostId = (TextView) view.findViewById(R.id.tv_host_id);
            holder.ivEvent = (ImageView) view.findViewById(R.id.iv_event);
            holder.ivHost = (ImageView) view.findViewById(R.id.iv_host);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Host host = hosts.get(i);
        String alias = host.getAlias();
        holder.tvHostName.setText(alias);
        holder.tvHostId.setText(host.getHostId());
        boolean isAlarm = host.getIsAlarm();
        boolean isError = host.isError();
        if (isError || isAlarm) {
            holder.ivEvent.setVisibility(View.VISIBLE);
        } else {
            holder.ivEvent.setVisibility(View.GONE);
        }
        return view;
    }

    private class Holder {
        TextView tvHostName;
        TextView tvHostId;
        ImageView ivEvent;
        ImageView ivHost;
    }
}
