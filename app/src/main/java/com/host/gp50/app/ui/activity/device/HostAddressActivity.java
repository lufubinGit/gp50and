package com.host.gp50.app.ui.activity.device;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.okgo.callbacklistener.OkGoEditDeviceListener;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.bean.Host;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.StatusbarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.device
 *
 * @author Administrator
 * @date 2017/12/19
 */

public class HostAddressActivity extends BaseActivity implements AMap.OnMyLocationChangeListener,
        AMap.OnMapLongClickListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(20, 0, 0, 180);
    private final int Location = 0;
    private final int LocationFail = 1;
    private final int LocationChange = 2;
    private final int ChangeSuccess = 3;

    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.ll_view)
    LinearLayout llView;

    private UiSettings mUiSettings;
    MyLocationStyle myLocationStyle;
    private BitmapDescriptor bitmapDescriptor;
    private MarkerOptions markerOption;
    private AMap aMap;
    private Marker firstMarker;
    private Marker secondMarker;
    private String alias = "";
    private String address = "";
    private View viewInfo;
    private PopupWindow popupWindow;
    private GeocodeSearch geocoderSearch;
    private LatLng changeLatLng;
    private LatLng oldLatLng;
    private Dialog dialog;
    private String newAddress;
    private boolean isOnLongClick;
    private boolean isMarkerMove;
    private Host host;
    private boolean isChangeAddress;

    private OkGoEditDeviceListener okGoEditDeviceListener = new OkGoEditDeviceListener() {
        @Override
        public void onCallBack(String content) {

            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                isChangeAddress = true;
                handler.sendEmptyMessageDelayed(ChangeSuccess, 1500);
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_address);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        OkGoManger.getInstance().setOkGoEditDeviceListener(okGoEditDeviceListener);

        Intent intent = getIntent();
        host = (Host) intent.getSerializableExtra(Api.selectHost);
        alias = host.getAlias();
        address = host.getHostAddress();
        mMapView.onCreate(savedInstanceState);

        //设置定位图标
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_location_icon);
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        mMapView.setVisibility(View.GONE);

        //逆地理编码
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        if (TextUtils.isEmpty(address)) {
            showNoneAddressDialog();
        } else {
            showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
            initMap();
            setCenter();
        }
    }

    private void initMap() {
        aMap = mMapView.getMap();
        mUiSettings = aMap.getUiSettings();
        //指南针用于向 App 端用户展示地图方向，默认不显示。通过如下接口控制其显示：
        mUiSettings.setCompassEnabled(true);
        //控制比例尺控件是否显示
        mUiSettings.setScaleControlsEnabled(true);

        // 显示实时交通状况
        aMap.setTrafficEnabled(false);
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        // 卫星地图模式
        //aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        //设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
        //改变地图的缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setInfoWindowAdapter(infoWindowAdapter);
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        // （1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        //定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        ////设置定位蓝点精度圆圈的填充颜色的方法。
        myLocationStyle.radiusFillColor(FILL_COLOR);
        //设置定位蓝点精度圆圈的边框颜色的方法
        myLocationStyle.strokeColor(STROKE_COLOR);
        //设置定位蓝点的Style
        myLocationStyle.myLocationIcon(bitmapDescriptor);
        myLocationStyle.anchor(0.5f, 1.0f);
        aMap.setMyLocationStyle(myLocationStyle);

        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);

        aMap.setOnMapLongClickListener(this);

        //绑定信息窗点击事件
        aMap.setOnInfoWindowClickListener(listener);

        // 绑定marker拖拽事件
        aMap.setOnMarkerDragListener(markerDragListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if (location != null) {
            Log.e("amap", "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                 /*
                errorCode
                errorInfo
                locationType
                */
                Log.e("amap", "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
                if (errorCode != 0) {
                    handler.sendEmptyMessageDelayed(LocationFail, 1500);
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            .position(latLng)
                            .draggable(true);
                    firstMarker = aMap.addMarker(markerOption);
                    getAddress(location.getLatitude(), location.getLongitude());
                    oldLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    changeCamera(
                            CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                    oldLatLng, 0, 0, 0)));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                } else {
                }
                handler.sendEmptyMessageDelayed(Location, 1500);

            } else {
                Log.e("amap", "定位信息， bundle is null ");
                handler.sendEmptyMessageDelayed(LocationFail, 1500);
            }
        } else {
            Log.e("amap", "定位失败");
            handler.sendEmptyMessageDelayed(LocationFail, 1500);
        }
    }

    public void setCenter() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        oldLatLng = new LatLng(host.getLatitude(), host.getLongitude());
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        oldLatLng, 0, 0, 0)));

        firstMarker = aMap.addMarker(new MarkerOptions().position(oldLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .draggable(true));
        firstMarker.showInfoWindow();
        getAddress(oldLatLng.latitude, oldLatLng.longitude);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }

    @Override
    public void handleMessageOnNext(Message msg) {
        super.handleMessageOnNext(msg);
        switch (msg.what) {
            case Location:
                tipDialog.dismiss();
                mMapView.setVisibility(View.VISIBLE);
                break;
            case LocationFail:
                tipDialog.dismiss();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_FAIL, getString(R.string.location_fail));
                mMapView.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                }, 1500);
                break;
            case LocationChange:
                tipDialog.dismiss();
                showChangeAddress();
                break;
            case ChangeSuccess:
                tipDialog.dismiss();
                isSuccess = true;
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_SUCCESS, getString(R.string.opt_success));
                handler.sendEmptyMessageDelayed(Cancel_Dialog, 1000);
                oldLatLng = changeLatLng;
                address = newAddress;
                firstMarker.hideInfoWindow();
                firstMarker.setPosition(changeLatLng);
                firstMarker.showInfoWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (latLng != null) {
            Log.e("amap", "onMapLongClick 长按地图， lat: " + latLng.latitude + " lon: " + latLng.longitude);
            if (firstMarker == null) {
                markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .position(latLng)
                        .draggable(true);
                firstMarker = aMap.addMarker(markerOption);
                getAddress(latLng.latitude, latLng.longitude);
            } else {

                isOnLongClick = true;
                changeLatLng = latLng;
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
                getAddress(latLng.latitude, latLng.longitude);
            }
        }
    }

    private void getAddress(double latitude, double longitude) {
        LatLonPoint latLonPoint = new LatLonPoint(latitude, longitude);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    AMap.InfoWindowAdapter infoWindowAdapter = new AMap.ImageInfoWindowAdapter() {
        View infoWindow = null;

        @Override
        public long getInfoWindowUpdateTime() {
            return 0;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            if (marker.equals(firstMarker)) {
                infoWindow = LayoutInflater.from(HostAddressActivity.this).
                        inflate(R.layout.layout_map_marker, null);
                TextView tittle = (TextView) infoWindow.findViewById(R.id.title);
                TextView snippet = (TextView) infoWindow.findViewById(R.id.snippet);
                tittle.setText(alias);
                snippet.setText(address);
                return infoWindow;
            }
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

    };

    AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker arg0) {
            Log.i("gp50", "onClick:" + 3333333);
        }
    };

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
            //城市
            String city = regeocodeAddress.getCity();
            //省
            String province = regeocodeAddress.getProvince();
            //区
            String district = regeocodeAddress.getDistrict();
            String adCode = regeocodeAddress.getAdCode();
            String building = regeocodeAddress.getBuilding();
            String formatAddress = regeocodeAddress.getFormatAddress();
            String neighborhood = regeocodeAddress.getNeighborhood();
            Log.i("gaode", "onRegeocodeSearched:" + "city:" + city + "..."
                    + "province:" + province + "..." + "district:" + district + "..."
                    + "adCode:" + adCode + "..." + "building:" + building + "..."
                    + "formatAddress:" + formatAddress + "..." + "neighborhood:" + neighborhood + "...");
            if (isOnLongClick || isMarkerMove) {
                newAddress = formatAddress;
                isOnLongClick = false;
                isMarkerMove = false;
                handler.sendEmptyMessageDelayed(LocationChange, 1500);
            } else {
                address = formatAddress;
                firstMarker.hideInfoWindow();
                firstMarker.setPosition(changeLatLng);
                firstMarker.showInfoWindow();
            }
        } catch (Exception e) {
            Log.i("gaode", "onRegeocodeSearched:" + "异常");
            handler.sendEmptyMessageDelayed(LocationFail, 1500);
        }

    }

    /**
     * 通过高德经纬度获取地址回调
     *
     * @param geocodeResult 地址
     * @param i             响应码
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        Log.i("gaode", "onGeocodeSearched:" + geocodeResult);
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }


    /**
     * 定义 Marker拖拽的监听
     */
    AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {

        // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDragStart(Marker arg0) {
            // TODO Auto-generated method stub
            isMarkerMove = true;
        }

        // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDragEnd(Marker arg0) {
            // TODO Auto-generated method stub
            changeLatLng = arg0.getPosition();
            firstMarker.setPosition(changeLatLng);
            firstMarker.hideInfoWindow();
            showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
            getAddress(changeLatLng.latitude, changeLatLng.longitude);
        }

        // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
        // 这个位置可能与拖动的之前的marker位置不一样。
        // marker 被拖动的marker对象。
        @Override
        public void onMarkerDrag(Marker arg0) {
            // TODO Auto-generated method stub

        }
    };

    private void showChangeAddress() {
        dialog = new Dialog(this, R.style.CenterDialogStyle);

        View layout = View.inflate(this, R.layout.dialog_change_address, null);

        TextView cancel = (TextView) layout.findViewById(R.id.cancel);
        TextView tvDeviceAddress = (TextView) layout.findViewById(R.id.tv_device_address);
        TextView confirm = (TextView) layout.findViewById(R.id.confirm);

        tvDeviceAddress.setText(newAddress);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                firstMarker.hideInfoWindow();
                firstMarker.setPosition(oldLatLng);
                firstMarker.showInfoWindow();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.process));
                OkGoManger.getInstance().editDevice(spf.getString(UserApi.USER_ID, ""),
                        host.getHostId(), "", changeLatLng.longitude + "",
                        changeLatLng.latitude + "", newAddress,
                        spf.getString(UserApi.TOKEN, ""));
            }
        });

        dialog.setContentView(layout);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = screenWidth * 3 / 5;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showNoneAddressDialog() {
        dialog = new Dialog(this, R.style.CenterDialogStyle);
        View layout = View.inflate(this, R.layout.dialog_none_address, null);

        TextView confirm = (TextView) layout.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                initMap();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.loading));
            }
        });

        dialog.setContentView(layout);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = screenWidth * 3 / 5;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void finish() {
        if (isChangeAddress) {
            isChangeAddress = false;
            Intent intent = new Intent();
            intent.putExtra(Api.LONGITUDE, oldLatLng.longitude);
            intent.putExtra(Api.LATITUDE, oldLatLng.latitude);
            intent.putExtra(Api.ADDRESS, address);
            setResult(RESULT_OK, intent);
        }
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }

    @OnClick(R.id.tittleBack)
    public void onViewClicked() {
        finish();
    }
}
