package com.host.gp50.app.okgo;

import android.app.Application;
import android.util.Log;

import com.host.gp50.app.Urls;
import com.host.gp50.app.okgo.callbacklistener.OkGoBindHostListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoCallBackListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoEditDeviceListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoEditUserInfoListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoGetBindHostListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoGetHostStateListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoHistoryListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoLogOutListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoLoginListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoOptDeviceListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoQueryDeviceSubUserListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoRegisterListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoShareDeviceListener;
import com.host.gp50.app.okgo.callbacklistener.OkGoUnbindDeviceListener;
import com.host.gp50.app.utils.SharedPreferencesHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;


/**
 * com.host.gp20.gprs.app.okgo
 *
 * @author Administrator
 * @date 2017/12/12
 */
public class OkGoManger {

    private static OkGoManger okGoManger;
    private OkGoCallBackListener okGoCallBackListener;
    private OkGoGetBindHostListener okGoGetBindHostListener;
    private OkGoBindHostListener okGoBindHostListener;
    private OkGoHistoryListener okGoHistoryListener;
    private OkGoRegisterListener okGoRegisterListener;
    private OkGoLoginListener okGoLoginListener;
    private OkGoGetHostStateListener okGoGetHostStateListener;
    private OkGoEditDeviceListener okGoEditDeviceListener;
    private OkGoOptDeviceListener okGoOptDeviceListener;
    private OkGoLogOutListener okGoLogOutListener;
    private OkGoShareDeviceListener okGoShareDeviceListener;
    private OkGoEditUserInfoListener okGoEditUserInfoListener;
    private OkGoQueryDeviceSubUserListener okGoQueryDeviceSubUserListener;
    private OkGoUnbindDeviceListener okGoUnbindDeviceListener;


    public static OkGoManger getInstance() {
        if (okGoManger == null) {
            okGoManger = new OkGoManger();
        }
        return okGoManger;
    }

    public void cancel() {
        OkGo.getInstance().cancelTag(this);
    }

    public void initOKgo(Application context) {
        HttpHeaders headers = new HttpHeaders();
        headers.put(Urls.request_header, Urls.access_token);
        OkGo.init(context);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkGo")
                    //全局的连接超时时间      OkGo.DEFAULT_MILLISECONDS  60秒
                    .setConnectTimeout(30000)
                    //全局的读取超时时间
                    .setReadTimeOut(30000)
                    //全局的写入超时时间
                    .setWriteTimeOut(30000)
                    .setCacheMode(CacheMode.NO_CACHE)
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //如果不想让框架管理cookie,以下不需要
                    //cookie持久化存储，如果cookie不过期，则一直有效
                    .setCookieStore(new PersistentCookieStore());
            //设置全局公共头
//                    .addCommonHeaders(headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCallBackListener(OkGoCallBackListener okGoCallBackListener) {
        this.okGoCallBackListener = okGoCallBackListener;
    }

    public void setOkGoGetBindHostListener(OkGoGetBindHostListener okGoGetBindHostListener) {
        this.okGoGetBindHostListener = okGoGetBindHostListener;
    }

    public void setOkGoBindHostListener(OkGoBindHostListener okGoBindHostListener) {
        this.okGoBindHostListener = okGoBindHostListener;
    }

    public void setOkGoHistoryListener(OkGoHistoryListener okGoHistoryListener) {
        this.okGoHistoryListener = okGoHistoryListener;
    }

    public void setOkGoRegisterListener(OkGoRegisterListener okGoRegisterListener) {
        this.okGoRegisterListener = okGoRegisterListener;
    }

    public void setOkGoLoginListener(OkGoLoginListener okGoLoginListener) {
        this.okGoLoginListener = okGoLoginListener;
    }

    public void setOkGoGetHostStateListener(OkGoGetHostStateListener okGoGetHostStateListener) {
        this.okGoGetHostStateListener = okGoGetHostStateListener;
    }

    public void setOkGoEditDeviceListener(OkGoEditDeviceListener okGoEditDeviceListener) {
        this.okGoEditDeviceListener = okGoEditDeviceListener;
    }

    public void setOkGoOptDeviceListener(OkGoOptDeviceListener okGoOptDeviceListener) {
        this.okGoOptDeviceListener = okGoOptDeviceListener;
    }

    public void setOkGoLogOutListener(OkGoLogOutListener okGoLogOutListener) {
        this.okGoLogOutListener = okGoLogOutListener;
    }

    public void setOkGoShareDeviceListener(OkGoShareDeviceListener okGoShareDeviceListener) {
        this.okGoShareDeviceListener = okGoShareDeviceListener;
    }

    public void setOkGoEditUserInfoListener(OkGoEditUserInfoListener okGoEditUserInfoListener) {
        this.okGoEditUserInfoListener = okGoEditUserInfoListener;
    }

    public void setOkGoQueryDeviceSubUserListener(OkGoQueryDeviceSubUserListener okGoQueryDeviceSubUserListener) {
        this.okGoQueryDeviceSubUserListener = okGoQueryDeviceSubUserListener;
    }

    public void setOkGoUnbindDeviceListener(OkGoUnbindDeviceListener okGoUnbindDeviceListener) {
        this.okGoUnbindDeviceListener = okGoUnbindDeviceListener;
    }

    /**
     * 注册用户
     *
     * @param userName   用户名
     * @param password   用户密码
     * @param type       注册方式：“1”为手机号码注册，“2”为邮箱账号注册，“3”为普通注册
     * @param verifycode 验证码
     */
    public void registerUser(String userName, String password, String type, String verifycode) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("account", userName);
        params.put("password", password);
        params.put("type", type);
        params.put("verifycode", verifycode);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.REGISTER)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:registerUser:" + s);
                        okGoRegisterListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoRegisterListener.onError(e.toString());
                    }
                });
    }

    /**
     * 用户登录
     *
     * @param userName     用户名
     * @param userPassWord 用户密码
     * @param usertype     用户类型：“1”为普通用户，“2”为保安人员
     */
    public void userLogin(String userName, String userPassWord, String usertype) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", userPassWord);
        params.put("usertype", usertype);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.LOGIN)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:userLogin:" + s);
                        okGoLoginListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoLoginListener.onError(e.toString());
                    }
                });
    }

    /**
     * 退出登录
     *
     * @param userid 用户ID
     * @param token  token
     */
    public void userLogOut(String userid, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.LOG_OUT)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:userLogOut:" + s);
//                        okGoLogOutListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
//                        okGoLogOutListener.onError(e.toString());
                    }
                });
    }

    /**
     * 修改用户信息
     *
     * @param userid   userId
     * @param username userName
     * @param nickname 用户别名
     * @param picture  用户头像
     * @param address  用户地址
     * @param token    token
     */
    public void editUserInfo(String userid, String username, String nickname, String picture,
                             String address, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("username", username);
        params.put("nickname", nickname);
        params.put("picture", picture);
        params.put("address", address);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.EDIT_USER_INFO)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:editUserInfo:" + s);
//                        okGoEditUserInfoListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
//                        okGoEditUserInfoListener.onError(e.toString());
                    }
                });
    }

    /**
     * 绑定主机
     *
     * @param userId 用户ID
     * @param hostId 主机ID
     * @param token  token
     */
    public void bindHost(String hostId, String userId, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("hostid", hostId);
        params.put("userid", userId);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.BIND_HOST)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:bindHost:" + s);
                        okGoBindHostListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoBindHostListener.onError(e.toString());
                    }
                });
    }

    /**
     * 分享主机
     *
     * @param userId    主用户ID
     * @param deviceid  设备ID
     * @param subuserid 子用户ID
     * @param token     token
     */
    public void shareDevice(String userId, String deviceid, String subuserid, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("deviceid", deviceid);
        params.put("subuserid", subuserid);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.SHARE_DEVICE)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:shareDevice:" + s);
                        okGoShareDeviceListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoShareDeviceListener.onError(e.toString());
                    }
                });
    }

    /**
     * 查询主机的子用户
     *
     * @param deviceid 设备ID
     * @param token    token
     */
    public void queryDeviceSubUser(String deviceid, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceid", deviceid);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.QUERY_DEVICE_SUBUSER)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:queryDeviceSubUser:" + s);
//                        okGoQueryDeviceSubUserListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
//                        okGoQueryDeviceSubUserListener.onError(e.toString());
                    }
                });
    }

    /**
     * 查询用户绑定的设备
     *
     * @param userId userid
     * @param token  token
     */
    public void getBindHost(String userId, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.QUERY_BIND_HOST)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:getBindHost:" + s);
                        okGoGetBindHostListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoGetBindHostListener.onError(e.toString());
                    }
                });
    }

    public void getHostState(String userId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("bindState", "1");
        JSONObject jsonObject = new JSONObject(params);
        OkGo.post(Urls.QUERY_BIND_HOST)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:getBindHost:" + s);
                        okGoEditDeviceListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoEditDeviceListener.onError(e.toString());
                    }
                });
    }

    /**
     * 用户解绑主机
     *
     * @param userid   用户id
     * @param deviceid 主机id
     * @param token    token
     */
    public void unbindDevice(String userid, String deviceid, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("deviceid", deviceid);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject.toString());
        OkGo.post(Urls.UNBIND_DEVICE)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:" + s);
                        okGoUnbindDeviceListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + "错误了" + e.toString());
                        okGoUnbindDeviceListener.onError(e.toString());
                    }
                });
    }

    /**
     * 修改设备名称或地址
     *
     * @param userId     用户ID
     * @param hostId     主机ID
     * @param deviceName 主机名字，可为空
     * @param longitude  经度，可为空
     * @param latitude   纬度，可为空
     * @param location   主机地址描述，可为空
     * @param token      token
     */
    public void editDevice(String userId, String hostId, String deviceName, String longitude,
                           String latitude, String location, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("hostid", hostId);
        params.put("devicename", deviceName);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("location", location);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject);
        OkGo.post(Urls.EDIT_HOST)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onCallBack:" + s);
                        okGoEditDeviceListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + e.toString());
                        okGoEditDeviceListener.onError(e.toString());
                    }
                });
    }

    /**
     * 撤防，布防操作
     *
     * @param userId   用户ID
     * @param cmdtype  1	外出布防，2	留守布防，3	撤防，4	监听，5	对讲，6	开始学码，
     *                 7	退出学码，8	开警号，9	关警号，10	开PGM，17	关PGM
     * @param deviceid 设备ID
     * @param token    token
     */
    public void optDevice(String userId, String cmdtype, String deviceid, String token) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userId);
        params.put("cmdtype", cmdtype);
        params.put("deviceid", deviceid);
        params.put("token", token);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject);
        OkGo.post(Urls.OPT_HOST)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onCallBack:" + s);
                        okGoOptDeviceListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.i("okgo_test", "onError:" + e.toString());
                        okGoOptDeviceListener.onError(e.toString());
                    }
                });
    }


    /**
     * 发送透传数据
     *
     * @param hostId  主机ID
     * @param sn      SN
     * @param content 透传内容
     */
    public void penetrateContent(String hostId, String sn, String content, SharedPreferencesHelper sph) {
        HashMap<String, String> params = new HashMap<>();
        params.put("hostId", hostId);
        params.put("sn", sn);
        params.put("option1", sph.getString("userId", "") + "," + content);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject);
        OkGo.post(Urls.PUSH_MESSAGE)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onCallBack:" + s);
                        okGoCallBackListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        okGoCallBackListener.onError(e.toString());
                    }
                });
    }

    /**
     * 查询主机历史记录
     *
     * @param userid   用户ID
     * @param deviceid 设备ID
     * @param pagenum  页码
     * @param pagesize 每页的数据条数
     */
    public void selectHostHistory(String userid, String deviceid, String pagenum, String pagesize) {
        pagesize = "10";
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userid);
        params.put("deviceid", deviceid);
        params.put("pagenum", pagenum);
        params.put("pagesize", pagesize);
        JSONObject jsonObject = new JSONObject(params);
        Log.i("okgo_test", "jsonObject:" + jsonObject);
        OkGo.post(Urls.HOST_HISTORY)
                .tag(this)
                .upJson(jsonObject.toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //上传成功
                        Log.i("okgo_test", "onSuccess:selectHostHistory:" + s);
                        okGoHistoryListener.onCallBack(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        okGoHistoryListener.onError(e.toString());
                    }
                });
    }


    public static String GetInetAddress(String host) {
        String IPAddress = "";
        InetAddress ReturnStr1 = null;
        try {
            ReturnStr1 = InetAddress.getByName(host);
            IPAddress = ReturnStr1.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return IPAddress;
        }
        return IPAddress;
    }

}
