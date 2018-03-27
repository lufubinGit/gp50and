package com.host.gp50.app.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.host.gp50.app.Api;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.greendao.gen.HostDao;
import com.host.gp50.app.okgo.OkGoManger;
import com.host.gp50.app.okgo.callbacklistener.OkGoGetBindHostListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoOptDeviceListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoShareDeviceListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoUnbindDeviceListener;
import com.host.gp50.app.ui.activity.device.AddDeviceActivity;
import com.host.gp50.app.ui.activity.device.HostSettingActivity;
import com.host.gp50.app.ui.activity.device.sub.ShowSubDeviceActivity;
import com.host.gp50.app.ui.adapter.PopWindowAdapter;
import com.host.gp50.app.ui.bean.Host;
import com.host.gp50.app.ui.bean.HostBean;
import com.host.gp50.app.ui.view.dialog.QMUITipDialog;
import com.host.gp50.app.utils.DensityUtil;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;
import com.host.gp50.app.utils.wifi.WifiListener;
import com.host.gp50.app.utils.wifi.WifiUtils;
import com.zxing.CaptureActivity;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * com.host.gp50.app.ui.fragment
 *
 * @author Administrator
 * @date 2017/11/28
 */

public class DeviceFragment extends BaseFragment implements Animation.AnimationListener {

    private final int CANCEL = 0;
    private final int ROTATION = 1;

    private final int ARM_STATE = 0;
    private final int TIP_DIALOG = 1;

    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.iv_change_device)
    ImageView ivChangeDevice;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_history)
    ImageView ivHistory;
    @BindView(R.id.iv_arm_out)
    ImageView ivArmOut;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.iv_arm_in)
    ImageView ivArmIn;
    @BindView(R.id.ll_device_name)
    LinearLayout llDeviceName;
    @BindView(R.id.iv_dis_arm)
    ImageView ivDisArm;
    @BindView(R.id.iv_arm_away)
    ImageView ivArmAway;
    @BindView(R.id.iv_arm_stay)
    ImageView ivArmStay;
    @BindView(R.id.fl_device_inform)
    FrameLayout flDeviceInform;
    @BindView(R.id.tv_host_name)
    TextView tvHostName;
    @BindView(R.id.tv_sub_device_number)
    TextView tvSubDeviceNumber;
    @BindView(R.id.bt_look)
    Button btLook;
    @BindView(R.id.tv_host_user_number)
    TextView tvHostUserNumber;
    @BindView(R.id.bt_share)
    Button btShare;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.tv_no_host_desc)
    TextView tvNoHostDesc;
    @BindView(R.id.rl_add_device)
    RelativeLayout rlAddDevice;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.iv_host_event)
    ImageView ivHostEvent;
    @BindView(R.id.iv_xin_hao)
    ImageView ivXinHao;
    @BindView(R.id.slv)
    ScrollView slv;

    private int SHARE_HOST_CODE = 2;
    private View view_pop;
    private PopupWindow popupWindow;
    private int screenWidth;
    private int screenHeight;
    private AnimationSet outSet01;
    private AnimationSet outSet02;
    private Animation viewClickAnimation;
    private int armState = -1;
    private boolean isArm;
    private List<Host> hosts = new ArrayList<>();
    private Host selectHost;
    private String selectHostId;
    private PopWindowAdapter popWindowAdapter;
    private boolean isArmSuccess;
    private int newArmState;
    private WifiUtils wifiUtils;
    private boolean isDeleteSuccess;
    private HostDao hostDao;
    private String userid;
    private String token;

    private OkGoGetBindHostListener okGoGetBindHostListener = new OkGoGetBindHostListener() {
        @Override
        public void onCallBack(String content) {
            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                String data = JsonUtil.getValueByKey(content, "data");
                List<HostBean> hostBeans = JsonUtil.getJavaArray(data, HostBean.class);
                for (int i = 0; i < hostBeans.size(); i++) {
                    HostBean hostBean = hostBeans.get(i);
                    Host host = new Host();
                    hostBeanToHost(hostBean, host);
                    hosts.add(host);
                }
                popWindowAdapter.notifyDataSetChanged();
                getSelectHost();

                setData();

                isShowHostEvent();

            }
            setViewShow();
        }

        @Override
        public void onError(String error) {
//            setViewShow();
            ToastUtil.showToast(activity, error);
        }
    };

    private void isShowHostEvent() {
        boolean isHostEvent = false;
        for (Host host : hosts) {
            boolean isAlarm = host.getIsAlarm();
            boolean isError = host.isError();
            if (isAlarm || isError) {
                isHostEvent = true;
                break;
            }
        }
        if (isHostEvent) {
            ivHostEvent.setVisibility(View.VISIBLE);
        } else {
            ivHostEvent.setVisibility(View.INVISIBLE);
        }
    }

    private OkGoOptDeviceListener okGoOptDeviceListener = new OkGoOptDeviceListener() {
        @Override
        public void onCallBack(String content) {
            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                isArmSuccess = true;
                onCallBackMessage(activity.getString(R.string.success), Api.SUCCESS);
            } else {
                onCallBackMessage(JsonUtil.getValueByKey(content, "error"), Api.FAIL);
            }
        }

        @Override
        public void onError(String error) {
            onCallBackMessage(error, Api.ERROR);
        }
    };

    private OkGoUnbindDeviceListener okGoUnbindDeviceListener = new OkGoUnbindDeviceListener() {
        @Override
        public void onCallBack(String content) {
            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                onCallBackMessage(activity.getString(R.string.success), Api.SUCCESS);
                isDeleteSuccess = true;

            } else {
                onCallBackMessage(JsonUtil.getValueByKey(content, "error"), Api.FAIL);
            }
        }

        @Override
        public void onError(String error) {
            onCallBackMessage(error, Api.ERROR);
        }
    };

    private OkGoShareDeviceListener okGoShareDeviceListener = new OkGoShareDeviceListener() {
        @Override
        public void onCallBack(String content) {
            if (JsonUtil.getBooleanValueByKey(content, "result")) {
                onCallBackMessage(activity.getString(R.string.success), Api.SUCCESS);
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
    protected void handleMessageOnNext(Message msg) {
        String obj = (String) msg.obj;
        switch (msg.what) {
            case ARM_STATE:
                setArmStateImage();
                break;
            case TIP_DIALOG:
                tipDialog.dismiss();
                if (isArmSuccess) {
                    isArmSuccess = false;
                    armState = newArmState;
                    setArmStateImage();
                }
                if (isDeleteSuccess) {
                    isDeleteSuccess = false;
                    for (int i = 0; i < hosts.size(); i++) {
                        Host host = hosts.get(i);
                        if (host.getHostId().equals(selectHostId)) {
                            hosts.remove(i);
                            break;
                        }
                    }
                    if (hosts.size() > 0) {
                        selectHost = hosts.get(0);
                        selectHostId = hosts.get(0).getHostId();
                    } else {
                        selectHost = null;
                        selectHostId = "";
                    }
                    spf.putString("selectHostId", selectHostId);
                    setData();
                    setViewShow();
                }
                break;
            case Api.FAIL:
                tipDialog.dismiss();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_FAIL, obj);
                handler.sendEmptyMessageDelayed(TIP_DIALOG, 1500);
                break;
            case Api.SUCCESS:
                tipDialog.dismiss();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_SUCCESS, obj);
                handler.sendEmptyMessageDelayed(TIP_DIALOG, 1000);
                break;
            case Api.ERROR:
                tipDialog.dismiss();
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_FAIL, getString(R.string.fail));
                handler.sendEmptyMessageDelayed(TIP_DIALOG, 1500);
                ToastUtil.showToast(activity, (String) msg.obj);
                break;
            default:
                break;
        }
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_device, null);
        ButterKnife.bind(this, view);
        StatusbarUtils.initAfterSetContentView(getActivity(), tittleView);

        wifiUtils = new WifiUtils(activity);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        hostDao = daoSession.getHostDao();

        selectHostId = spf.getString("selectHostId", "");

        initPopWindow();

        setView();
        getDbHosts();
//        setData();
        return view;
    }

    /**
     * 主机jsonBean转换成bean
     *
     * @param hostBean JSONBean
     * @param host     Host
     */
    private void hostBeanToHost(HostBean hostBean, Host host) {
        host.setAlias(hostBean.getHostAlias());
        host.setHostId(hostBean.getHostId());
        //报警
        String hostAlarmCode = hostBean.getHostAlarmCode();
        if (!TextUtils.isEmpty(hostAlarmCode)) {
            if (TextUtils.equals("000", hostAlarmCode)) {
                host.setIsAlarm(false);
            } else {
                host.setIsAlarm(true);
            }
            host.setAlarmCode(hostAlarmCode);
        }
        //错误
        String hostErrorCode = hostBean.getHostErrorCode();
        if (!TextUtils.isEmpty(hostErrorCode)) {
            if (TextUtils.equals("000", hostErrorCode)) {
                host.setError(false);
            } else {
                host.setError(true);
            }
            host.setErrorCode(hostErrorCode);
        }
        String hostlongitude = hostBean.getHostlongitude();
        if (!TextUtils.isEmpty(hostlongitude)) {
            String[] split = hostlongitude.split(",");
            host.setLatitude(Double.valueOf(split[1]));
            host.setLongitude(Double.valueOf(split[0]));
        }

        host.setHostAddress(hostBean.getHostAdress());
        String hostSignal = hostBean.getHostSignal();
        //信号
        if (TextUtils.isEmpty(hostSignal)) {
            host.setSignal(0);
        } else {
            host.setSignal(Integer.parseInt(hostSignal));
        }
        //用户数量
        int userNum = hostBean.getUserNum();
        if (userNum == 0) {
            host.setHostUserNumber(1);
        } else {
            host.setHostUserNumber(userNum);
        }
        //子设备数量
        String subDeviceNum = hostBean.getSubDeviceNum();
        if (TextUtils.isEmpty(subDeviceNum)) {
            host.setSubNumber(0);
        } else {
            host.setSubNumber(Integer.parseInt(subDeviceNum));
        }
        //IP
        host.setHostIp(hostBean.getHostIp());
        //版本号
        host.setHostVersion(hostBean.getHostVersion());
        //是否在线
        host.setIsOnline(hostBean.isIsOnline());
        //设备地址
        host.setHostAddress(hostBean.getHostAdress());
        //服务商
        //是否是主用户
        host.setIsMainUser(hostBean.isIsMainUser());
        //布防状态
        String hostArmState = hostBean.getHostArmState();
        if (!TextUtils.isEmpty(hostArmState)) {
            host.setArmState(Integer.parseInt(hostArmState));
        }
    }

    /**
     * 获取本地数据缓存的主机列表
     */
    public void getDbHosts() {
        userid = spf.getString(UserApi.USER_ID, "");
        token = spf.getString(UserApi.TOKEN, "");
        QueryBuilder hostQueryBuilder = hostDao.queryBuilder();
        List<Host> dbHosts = hostQueryBuilder.where(HostDao.Properties.UserId.eq(userid)).list();
        hosts.clear();

        for (Host host : dbHosts) {
            hosts.add(host);
        }
        popWindowAdapter.notifyDataSetChanged();
        setData();
        setViewShow();
    }

    /**
     * 获取用户绑定的主机列表
     */
    public void getHosts() {
        userid = spf.getString(UserApi.USER_ID, "");
        token = spf.getString(UserApi.TOKEN, "");
        hosts.clear();
        Log.i("okgo_test", "getHosts:" + userid + "..." + token);
        OkGoManger.getInstance().getBindHost(userid, token);
        //22.76402215645858, 113.88870298862459
        Host host = new Host(null, "123456789", "test01", "123456789",
                "jade", "0001",
                "xinghuang", "1.0", "192.168.1.1",
                1, 18, 2, "000",
                "000", 1, 1,
                22.76402215645858, 113.88870298862459, true);
        host.setIsOnline(true);
        Host host1 = new Host(null, "123456789", "test02", "987654321",
                "jade", "0001",
                "xinghuang", "1.0", "192.168.1.1",
                1, 28, 3, "000",
                "000", 2, 2,
                22.76402215645858, 113.88870298862459, false);
//        hosts.add(host);
//        hosts.add(host1);
    }

    private void getHostState() {

    }

    /**
     * 获取选中的主机对象
     */
    private void getSelectHost() {
        boolean isSelect = false;
        for (Host host : hosts) {
            String hostId = host.getHostId();
            if (TextUtils.equals(hostId, selectHostId)) {
                isSelect = true;
                selectHost = host;
                selectHostId = host.getHostId();
                break;
            }
        }
        if (!isSelect) {
            selectHost = hosts.get(0);
            selectHostId = hosts.get(0).getHostId();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        OkGoManger.getInstance().setOkGoGetBindHostListener(okGoGetBindHostListener);
        OkGoManger.getInstance().setOkGoOptDeviceListener(okGoOptDeviceListener);
        OkGoManger.getInstance().setOkGoUnbindDeviceListener(okGoUnbindDeviceListener);
        OkGoManger.getInstance().setOkGoShareDeviceListener(okGoShareDeviceListener);
        EventBus.getDefault().register(this);
        initViewClickAnimation();
        if (activity.isShare()) {
            String subUserId = activity.getShareUserId();
            showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, getString(R.string.process));
            OkGoManger.getInstance().shareDevice(userid, selectHostId, subUserId, token);
            activity.setShare(false);
        } else {
            getHosts();
        }
    }

    private void setViewShow() {
        if (hosts.size() != 0) {
            tittleView.setVisibility(View.VISIBLE);
            ivArmOut.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.VISIBLE);
            rlAddDevice.setVisibility(View.GONE);
            llDeviceName.setVisibility(View.VISIBLE);
            tvNoHostDesc.setVisibility(View.GONE);
            initOutAnimation();
        } else {
            if (outSet01 != null) {
                outSet01 = null;
            }
            if (outSet02 != null) {
                outSet02 = null;
            }
            llDeviceName.setVisibility(View.INVISIBLE);
            Glide.with(this).load(R.drawable.host_offline).into(ivArmIn);
            tittleView.setVisibility(View.INVISIBLE);
            ivArmOut.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
            rlAddDevice.setVisibility(View.VISIBLE);
            tvNoHostDesc.setVisibility(View.VISIBLE);
            tvNoHostDesc.setWidth((int) (screenWidth * 0.8));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        OkGoManger.getInstance().cancel();
        viewClickAnimation = null;
        if (outSet01 != null) {
            outSet01 = null;
        }
        if (outSet02 != null) {
            outSet02 = null;
        }
    }

    public void onEvent(Object event) {
        if (event instanceof String) {
            String s = (String) event;
            getHosts();
        }
    }

    private void setData() {
        popWindowAdapter.notifyDataSetChanged();
        if (selectHost == null) {
            return;
        }
        String alias = selectHost.getAlias();
        //设置名称
        tvHostName.setText(alias);
        ViewGroup.LayoutParams layoutParams = tvHostName.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        tvHostName.setLayoutParams(layoutParams);
        //设置信号
        int signal = selectHost.getSignal();
        if (signal <= 0) {
            ivXinHao.setImageResource(R.drawable.xinhao00);
        } else if (signal >= 1 && signal <= 7) {
            ivXinHao.setImageResource(R.drawable.xinhao01);
        } else if (signal >= 8 && signal <= 15) {
            ivXinHao.setImageResource(R.drawable.xinhao02);
        } else if (signal >= 16 && signal <= 23) {
            ivXinHao.setImageResource(R.drawable.xinhao03);
        } else if (signal >= 24 && signal <= 31) {
            ivXinHao.setImageResource(R.drawable.xinhao04);
        }
        //设置布防状态图片
        armState = selectHost.getArmState();
        setArmStateImage();
        //设置主机状态描述文字
        String alarmCode = selectHost.getAlarmCode();
        String errorCode = selectHost.getErrorCode();
        //设置子设备数量
        int subNumber = selectHost.getSubNumber();
        tvSubDeviceNumber.setText(String.valueOf(subNumber));
        //设置用户数量
        int hostUserNumber = selectHost.getHostUserNumber();
        tvHostUserNumber.setText(activity.getString(R.string.user_number) + hostUserNumber);
        if (selectHost.getIsMainUser()) {
            btShare.setVisibility(View.VISIBLE);
        } else {
            btShare.setVisibility(View.GONE);
        }
    }

    private void setView() {
        int statusBarHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBarHeight = StatusbarUtils.getStatusBarHeight(getActivity());
        } else {
            statusBarHeight = 0;
        }
        //设置背景色高度
        DensityUtil.setViewLayoutParams(back, (int) (screenWidth * 0.8 + statusBarHeight),
                ViewGroup.LayoutParams.MATCH_PARENT);
        back.setPadding(0, statusBarHeight, 0, 0);

        //设置报警状态背景图片宽高
        DensityUtil.setViewLayoutParams(ivArmOut,
                (int) (screenWidth * 0.8 * 0.5), (int) (screenWidth * 0.8 * 0.5));
        DensityUtil.setViewLayoutParams(ivArmIn,
                (int) (screenWidth * 0.8 * 0.5), (int) (screenWidth * 0.8 * 0.5));

        DensityUtil.setMargins(llDeviceName,
                0, (int) (screenWidth * 0.8 * 0.5 * 0.3) / 3 * 2, 0, 0);
        DensityUtil.setMargins(tvNoHostDesc,
                0, (int) (screenWidth * 0.8 * 0.5 * 0.3), 0, 0);
        ivChangeDevice.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = ivChangeDevice.getLayoutParams();
                ivChangeDevice.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int[] position = new int[2];
                ivChangeDevice.getLocationOnScreen(position);
                RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) ivHostEvent.getLayoutParams();
                layoutParams1.leftMargin = position[0] + layoutParams.width - layoutParams1.width / 2 - 5;
                ivHostEvent.setLayoutParams(layoutParams1);
            }
        });

    }

    @OnClick({R.id.iv_change_device, R.id.iv_setting, R.id.iv_history, R.id.iv_dis_arm,
            R.id.iv_arm_away, R.id.iv_arm_stay, R.id.bt_look, R.id.bt_share, R.id.bt_delete,
            R.id.rl_add_device, R.id.iv_arm_in})
    public void onViewClicked(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_change_device:
                //切换主机
                //设置popupWindow的位置  ivChangeDevice:为左上角图标，居于其下方
                popupWindow.showAsDropDown(ivChangeDevice, -10, -10);
                ivChangeDevice.startAnimation(viewClickAnimation);
                break;
            case R.id.iv_setting:
                //设置主机
                ivSetting.startAnimation(viewClickAnimation);
                intent = new Intent(activity, HostSettingActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(Api.selectHost, selectHost);
                intent.putExtras(bundle);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.iv_history:
                //历史记录
                ivHistory.startAnimation(viewClickAnimation);
                break;
            case R.id.ll_pop_add_device:
                //增加设备
                break;
            case R.id.iv_dis_arm:
                //撤防
                newArmState = Api.DIS_ARM;
                isArm = true;
                ivDisArm.startAnimation(viewClickAnimation);
                OkGoManger.getInstance().optDevice(userid, Api.DIS_ARM + "", selectHostId, token);
                break;
            case R.id.iv_arm_away:
                //外出布防
                newArmState = Api.ARM_AWAY;
                isArm = true;
                ivArmAway.startAnimation(viewClickAnimation);
                OkGoManger.getInstance().optDevice(userid, Api.ARM_AWAY + "", selectHostId, token);
                break;
            case R.id.iv_arm_stay:
                //留守布防
                newArmState = Api.ARM_STAY;
                isArm = true;
                ivArmStay.startAnimation(viewClickAnimation);
                OkGoManger.getInstance().optDevice(userid, Api.ARM_STAY + "", selectHostId, token);
                break;
            case R.id.bt_look:
                //查看子设备
                btLook.startAnimation(viewClickAnimation);
                intent = new Intent(activity, ShowSubDeviceActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.bt_share:
                //分享设备
                btShare.startAnimation(viewClickAnimation);
                //_{"userid":"22","deviceid":"01001000105000178954"}$\r
                intent = new Intent(activity, CaptureActivity.class);
                intent.putExtra("MODULE", SHARE_HOST_CODE);
                activity.startActivityForResult(intent, SHARE_HOST_CODE);
                activity.overridePendingTransition(R.anim.anim_y_in, R.anim.anim_stay);
                break;
            case R.id.bt_delete:
                //删除设备
                btDelete.startAnimation(viewClickAnimation);
                slv.scrollTo(0, 0);
                showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, activity.getString(R.string.process));
                OkGoManger.getInstance().unbindDevice(userid, selectHostId, token);
                break;
            case R.id.rl_add_device:
                //添加设备
                intent = new Intent(activity, AddDeviceActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.iv_arm_in:
                //报警状态图片
                if (!selectHost.getIsOnline()) {
                    break;
                }
                if (selectHost.getAlarmCode().equals("000") && selectHost.getErrorCode().equals("000")) {
                    break;
                }
                break;
            default:
                break;
        }
    }

    private void setArmStateImage() {
        if (selectHost == null) {
            return;
        }
        switch (armState) {
            case Api.ARM_AWAY:
                ivArmAway.setSelected(true);
                ivArmStay.setSelected(false);
                ivDisArm.setSelected(false);
                break;
            case Api.ARM_STAY:
                ivArmAway.setSelected(false);
                ivArmStay.setSelected(true);
                ivDisArm.setSelected(false);
                break;
            case Api.DIS_ARM:
                ivArmAway.setSelected(false);
                ivArmStay.setSelected(false);
                ivDisArm.setSelected(true);
                break;
            default:
                break;
        }

        if (!selectHost.getIsOnline()) {
            Glide.with(this).load(R.drawable.host_offline).into(ivArmIn);
            ivXinHao.setImageResource(R.drawable.xinhao00);
//            ivArmIn.setImageResource(R.drawable.host_offline);
            return;
        }

        if (selectHost.getIsAlarm()) {
            Glide.with(this).load(R.drawable.host_alarm).into(ivArmIn);
        } else if (selectHost.isError()) {
            Glide.with(this).load(R.drawable.host_error).into(ivArmIn);
        } else {
            switch (armState) {
                case Api.DIS_ARM:
                    Glide.with(this).load(R.drawable.dis_arm02).into(ivArmIn);
                    break;
                case Api.ARM_STAY:
                    Glide.with(this).load(R.drawable.arm_stay02).into(ivArmIn);
                    break;
                case Api.ARM_AWAY:
                    Glide.with(this).load(R.drawable.arm_away02).into(ivArmIn);
                    break;
                default:
                    break;
            }
        }
    }

    private void initPopWindow() {
        view_pop = LayoutInflater.from(activity).inflate(
                R.layout.pop_menu, null);

        LinearLayout popAddDevice = (LinearLayout) view_pop.findViewById(R.id.ll_pop_add_device);
        ListView lvPop = (ListView) view_pop.findViewById(R.id.lv_pop);

        popWindowAdapter = new PopWindowAdapter(activity, hosts);
        lvPop.setAdapter(popWindowAdapter);

        lvPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                Host host = hosts.get(i);
                selectHostId = host.getHostId();
                spf.putString("selectHostId", selectHostId);
                selectHost = host;
                setData();
            }
        });

        popAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(activity, AddDeviceActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            }
        });

        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        if (popupWindow == null) {
            //新建一个popupWindow
            popupWindow = new PopupWindow(view_pop,
                    width / 12 * 5,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            //设置popupWindow的背景颜色
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            //设置popupWindow的动画
            popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }

    private void initViewClickAnimation() {
        viewClickAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        viewClickAnimation.setDuration(100);
        viewClickAnimation.setAnimationListener(this);
    }

    private void initOutAnimation() {
        Animation animation01 = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation animation02 = new AlphaAnimation(1.0f, 0.1f);
        Animation animation03 = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        Animation animation04 = new AlphaAnimation(0.1f, 1.0f);
        outSet01 = new AnimationSet(true);
        outSet01.addAnimation(animation01);
        outSet01.addAnimation(animation02);
        outSet01.setDuration(2000);

        outSet02 = new AnimationSet(true);
        outSet02.addAnimation(animation03);
        outSet02.addAnimation(animation04);
        outSet02.setDuration(2000);
        outSet01.setAnimationListener(this);
        outSet02.setAnimationListener(this);

        ivArmOut.startAnimation(outSet01);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation.equals(viewClickAnimation) && isArm) {
            isArm = false;
            showTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING, activity.getString(R.string.process));
        }
        if (animation.equals(outSet01)) {

            if (outSet02 != null) {
                ivArmOut.startAnimation(outSet02);
            }
        }
        if (animation.equals(outSet02)) {
            if (outSet01 != null) {
                ivArmOut.startAnimation(outSet01);
            }
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void regist() {
        wifiUtils.register(new WifiListener() {

            @Override
            public void wifiOpen() {
                Log.i("haha", "wifiOpen");
            }

            @Override
            public void wifiNotConnect(NetworkInfo networkInfo) {
                Log.i("haha", "wifiNotConnect");

            }

            @Override
            public void wifiConnected(NetworkInfo networkInfo) {
                Log.i("haha", "wifiConnected");

            }

            @Override
            public void wifiClose() {
                Log.i("haha", "wifiClose");
            }

            @Override
            public void notConnected() {
                Log.i("haha", "notConnected");

            }

            @Override
            public void connected(ConnectivityManager manager, NetworkInfo networkInfo) {
                Log.i("haha", "connected");

            }
        });
    }
}
