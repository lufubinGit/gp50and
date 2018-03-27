package com.host.gp50.app.ui.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.bean.Host;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2017/12/07
 */

public class HostSettingActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener {

    private final int RENAME = 1;
    private final int SERVICE = 2;
    private final int ADDRESS = 3;


    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.tv_device_alias)
    TextView tvDeviceAlias;
    @BindView(R.id.ll_device_alias)
    LinearLayout llDeviceAlias;
    @BindView(R.id.ll_phone_number)
    LinearLayout llPhoneNumber;
    @BindView(R.id.ll_sms_number)
    LinearLayout llSmsNumber;
    @BindView(R.id.ll_pgm)
    LinearLayout llPgm;
    @BindView(R.id.ll_auto_arm)
    LinearLayout llAutoArm;
    @BindView(R.id.ll_sub_user)
    LinearLayout llSubUser;
    @BindView(R.id.tv_device_service_provider)
    TextView tvDeviceServiceProvider;
    @BindView(R.id.ll_service_provider)
    LinearLayout llServiceProvider;
    @BindView(R.id.tv_device_address)
    TextView tvDeviceAddress;
    @BindView(R.id.ll_device_address)
    LinearLayout llDeviceAddress;
    @BindView(R.id.tv_device_ip)
    TextView tvDeviceIp;
    @BindView(R.id.ll_device_ip)
    LinearLayout llDeviceIp;
    @BindView(R.id.tv_device_version)
    TextView tvDeviceVersion;
    @BindView(R.id.ll_device_version)
    LinearLayout llDeviceVersion;
    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceId;
    @BindView(R.id.ll_device_id)
    LinearLayout llDeviceId;

    private GeocodeSearch geocoderSearch;
    private Host host;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_setting);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        Intent intent = getIntent();
        host = (Host) intent.getSerializableExtra(Api.selectHost);

        //逆地理编码
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        initView();
    }

    private void initView() {
        tvDeviceAddress.setMaxWidth(screenWidth / 2);
        tvDeviceAlias.setText(host.getAlias());
        tvDeviceServiceProvider.setText(host.getHostServiceProvider());
        tvDeviceIp.setText(host.getHostIp());
        tvDeviceVersion.setText(host.getHostVersion());
        tvDeviceId.setText(host.getHostId());
//        if (host.getLatitude() != null && host.getLongitude() != null) {
//            getAddress(host.getLatitude(), host.getLongitude());
//        }
        tvDeviceAddress.setText(host.getHostAddress());
    }

    @OnClick({R.id.ll_device_alias, R.id.ll_phone_number, R.id.ll_sms_number,
            R.id.ll_pgm, R.id.ll_auto_arm, R.id.ll_sub_user, R.id.ll_service_provider,
            R.id.ll_device_address, R.id.ll_device_ip, R.id.ll_device_version, R.id.tittleBack,
            R.id.ll_device_id})
    public void onViewClicked(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.tittleBack:
                finish();
                break;
            case R.id.ll_device_alias:
                //设备别名
                intent = new Intent(this, HostRenameActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivityForResult(intent, RENAME);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_phone_number:
                //语音电话号码
                intent = new Intent(this, HostPhoneActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_sms_number:
                //短信电话号码
                intent = new Intent(this, HostSmsActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_pgm:
                //PGM
                intent = new Intent(this, HostPgmSetActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_auto_arm:
                //自动布撤防
                intent = new Intent(this, HostTimingPlanActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_sub_user:
                //子用户
                intent = new Intent();
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                break;
            case R.id.ll_service_provider:
                //服务商
                intent = new Intent(this, HostServiceProviderActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_device_address:
                //设备地址
                intent = new Intent(this, HostAddressActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, host);
                intent.putExtras(bundle);
                startActivityForResult(intent,ADDRESS);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_device_ip:
                //设备IP
                break;
            case R.id.ll_device_version:
                //设备版本号
                break;
            case R.id.ll_device_id:
                //设备ID
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RENAME:
                    String newName = data.getStringExtra(Api.RENAME);
                    host.setAlias(newName);
                    //TODO 更新数据库

                    tvDeviceAlias.setText(newName);
                    break;
                case SERVICE:
                    break;
                case ADDRESS:
                    double longitude = data.getDoubleExtra(Api.LONGITUDE,0);
                    double latitude = data.getDoubleExtra(Api.LATITUDE,0);
                    String address = data.getStringExtra(Api.ADDRESS);
                    host.setLongitude(longitude);
                    host.setLatitude(latitude);
                    host.setHostAddress(address);
                    tvDeviceAddress.setText(host.getHostAddress());
                    break;
                default:
                    break;
            }
        }
    }

    private void getAddress(double latitude, double longitude) {
        LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 通过经纬度获取地址回调
     *
     * @param regeocodeResult 地址
     * @param i               响应码
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        try {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();

            String formatAddress = regeocodeAddress.getFormatAddress();
            Log.i("onRegeocodeSearched","onRegeocodeSearched:" + formatAddress);
            tvDeviceAddress.setText(formatAddress);
            host.setHostAddress(formatAddress);
        } catch (Exception e) {
            Log.i("gaode", "onRegeocodeSearched:" + "异常");
//            tvDeviceAddress.setText("");
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }

}
