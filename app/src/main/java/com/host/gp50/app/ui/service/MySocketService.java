package com.host.gp50.app.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.host.gp50.app.MyApplication;
import com.host.gp50.app.R;
import com.host.gp50.app.UserApi;
import com.host.gp50.app.greendao.gen.DaoSession;
import com.host.gp50.app.ui.activity.MainActivity;
import com.host.gp50.app.utils.JsonUtil;
import com.host.gp50.app.utils.SharedPreferencesHelper;
import com.host.gp50.app.utils.StringUtil;
import com.host.gp50.app.utils.wifi.WifiListener;
import com.host.gp50.app.utils.wifi.WifiUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

import static java.lang.Thread.sleep;

/**
 * socket服务
 * Created by Administrator on 2017/9/01.
 */
public class MySocketService extends Service {
    private final static String TAG = "SocketService";
    //    String HOST = "118.250.183.229";
    String HOST;
    static int Countdown = 60 * 5;
    int PORT = 8989;
    int heartTime = Countdown - 5;
    int readTime = Countdown;
    private OutputStream outputStream;
    private Socket socket;
    private InputStream inputStream;
    private SharedPreferencesHelper spf;

    /**
     * 定时唤醒的时间间隔，这里为了自己测试方边设置了一分钟
     */
    private final static int ALARM_INTERVAL = 1 * 60 * 1000;
    // 发送唤醒广播请求码
    private final static int WAKE_REQUEST_CODE = 5121;
    // 守护进程 Service ID
    private final static int DAEMON_SERVICE_ID = -5121;

    private boolean isLogin;
    private Thread heartThread;
    private Thread readMsgThread;
    private boolean isGetSn = true;

    private Timer timer;
    private boolean isCallBack;
    private final int TIMER = 1;
    private final int SOCKET = 2;
    private final int OPEN_SOCKET = 3;
    private final int WIFI_CHANGE = 4;
    private final int LOG_OUT = 5;
    private boolean isLogOut;
    private boolean isCreate;
    private int errTime;
    private boolean isWifiClose;
    private WifiUtils wifiUtils;
    private DaoSession daoSession;

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER:
                    heartTime--;
                    readTime--;
                    if (readTime < 0) {

//                        isLogin = true;
                        handler.sendEmptyMessageDelayed(WIFI_CHANGE, 1000);
                        readTime = Countdown;
                    }
                    break;
                case SOCKET:
                    if (heartThread == null) {
                        heartThread = new Thread(new HeartThread());
                        heartThread.start();
                    }
                    if (readMsgThread == null) {
                        readMsgThread = new Thread(new ReadMsgThread());
                        readMsgThread.start();
                    }
                    isStartTimer();
                    break;
                case OPEN_SOCKET:
                    openSocket();
                    isLogin = true;
                    break;
                case WIFI_CHANGE:
                    if (!isWifiClose) {
                        closeSocket();
                        Log.i(TAG, "关闭Socket...WIFI_CHANGE");
                        heartTime = 5;
                        openSocket();
                        isLogin = true;
                    }
                    break;
                case LOG_OUT:
                    handler.sendEmptyMessageDelayed(WIFI_CHANGE, 1000);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        spf = SharedPreferencesHelper.getInstance(getApplicationContext());
        daoSession = MyApplication.instances.getDaoSession();

        isCreate = true;
        wifiUtils = new WifiUtils(this);
        regist();
        isLogin = true;
//        HOST = spf.getString("StringHost", "119.23.233.134");
        HOST = "119.23.233.134";
        PORT = 8989;
//        PORT = spf.getInt("SocketPort", 8989);
        Log.i(TAG, "onCreate:" + HOST + "..." + PORT);

        openSocket();
    }

    private void openSocket() {
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {
                    Log.i("Socket:", "创建Socket");
                    socket = new Socket(HOST, PORT);
                    Log.i(TAG, "onCreate:" + MySocketService.this.socket.isConnected() + "..." + MySocketService.this.socket.isClosed());
                    MySocketService.this.socket.setTcpNoDelay(true);
                    MySocketService.this.socket.setKeepAlive(true);
                    inputStream = MySocketService.this.socket.getInputStream();
                    outputStream = MySocketService.this.socket.getOutputStream();
                    handler.sendEmptyMessage(SOCKET);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 利用 Android 漏洞提高进程优先级，
        startForeground(DAEMON_SERVICE_ID, new Notification());

        // 当 SDk 版本大于18时，需要通过内部 Service 类启动同样 id 的 Service
        if (Build.VERSION.SDK_INT >= 18) {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(DAEMON_SERVICE_ID, new Notification());
        } else {
            //API < 18 ，此方法能有效隐藏Notification上的图标
            startForeground(DAEMON_SERVICE_ID, new Notification());
        }

        return START_STICKY;
    }

    //_{"userid":"22","deviceid":"01001000105000178954"}$\r

    /**
     * 拼写心跳内容
     *
     * @return 心跳内容
     */
    private String writeContent() {
        String token = spf.getString(UserApi.TOKEN, "");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("type", "1");
        JSONObject jsonObject = new JSONObject(params);
        return "_" + jsonObject.toString() + "$\r";
    }

    private String writeHeartContent() {
        String userid = spf.getString(UserApi.USER_ID, "");
        HashMap<String, Object> params = new HashMap<>();
        params.put("userid", userid);
        params.put("type", "3");
        JSONObject jsonObject = new JSONObject(params);
        return "_" + jsonObject.toString() + "$\r";
    }

    /**
     * 获取长连接内容
     *
     * @param content 长连接内容
     * @return 截取后的内容
     */
    private String getContent(String content) {
        try {
            String replace = content.replace("_", "").replace("$\\r", "");
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 心跳线程
     */
    private class HeartThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (outputStream != null) {
                    try {
                        sleep(1);
                        if (isLogin) {
                            readTime = 5;
                            String s = writeContent();
                            Log.i(TAG, "发送的数据:" + s);
                            sendMsg(s);
                            isLogin = false;
                        } else {
                            if (isCallBack) {
                                isCallBack = false;
                            }

                            if (heartTime <= 0) {
                                String s = writeHeartContent();
                                Log.i(TAG, "发送的数据:" + s);
                                sendMsg(s);
                                heartTime = Countdown - 5;
                            }
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 读数据
     */
    private class ReadMsgThread implements Runnable {

        @Override
        public void run() {
            String str = null;
            while (true) {
                try {
                    sleep(1);
                    if (inputStream != null) {
                        if (socket.isConnected()) {
                            if (inputStream.available() > 0) {
                                readTime = Countdown;
                                //创建字节数组
                                byte[] buffer = new byte[inputStream.available()];
                                inputStream.read(buffer);
                                String content = new String(buffer);
                                Log.i(TAG, "接收的数据:" + content);
                                if (content.startsWith("_") && content.endsWith("$\r")) {
                                    if (!content.contains("$\r_")) {
                                        String replace = content.replace("_", "").replace("$\n", "");
                                        String type = JsonUtil.getValueByKey(replace, "type");
                                        switch (type) {
                                            case "1":
                                                //登录
                                                if (!JsonUtil.getBooleanValueByKey(content, "result")) {
                                                    isLogin = true;
                                                }
                                                break;
                                            case "2":
                                                String pushContent = "";
                                                isCallBack = true;
                                                //消息ID
                                                String messageid = JsonUtil.getValueByKey(content, "messageid");
                                                //消息时间
                                                String time = JsonUtil.getValueByKey(replace, "servertime");
                                                String online = JsonUtil.getValueByKey(replace, "online");
                                                String data = JsonUtil.getValueByKey(replace, "data");
                                                String deviceid = JsonUtil.getValueByKey(data, "deviceid");
                                                if (online.equals("0")) {
                                                    String cidtype = JsonUtil.getValueByKey(data, "cidtype");
                                                    String cidvalue = JsonUtil.getValueByKey(data, "cidvalue");
                                                    String depart = JsonUtil.getValueByKey(data, "depart");
                                                    int optcode = JsonUtil.getIntValueByKey(data, "optcode");
                                                    pushContent = StringUtil.resolveCid(MyApplication.golbalContext,
                                                            cidtype, cidvalue, depart, optcode);
                                                } else {
                                                    if (online.equals("1")) {
                                                        //主机上线
                                                        pushContent = MyApplication.instances.getString(R.string.online);
                                                    }
                                                    if (online.equals("2")) {
                                                        //主机离线
                                                        pushContent = MyApplication.instances.getString(R.string.offline);
                                                    }
                                                }
                                                sendNotification(deviceid, pushContent + "  " + time);
                                                EventBus.getDefault().post(deviceid);
                                                break;
                                            case "3":
                                                //心跳包
                                                if (!JsonUtil.getBooleanValueByKey(content, "result")) {
                                                    handler.sendEmptyMessageDelayed(WIFI_CHANGE, 1000);
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析心跳包数据
     */
    private void resolveData(String data) {

    }

    public void sendMsg(String content) {
        try {
            //写入输出流
            outputStream.write(content.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            closeSocket();
            Log.i(TAG, "关闭Socket...写入输出流" + e);
//            isLogin = true;
            handler.sendEmptyMessageDelayed(WIFI_CHANGE, 30000);
        }
    }

    public void closeSocket() {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        closeSocket();
        Log.i(TAG, "关闭Socket...onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        closeSocket();
        Log.i(TAG, "关闭Socket...onDestroy");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    public void sendNotification(String hostId, String text) {
//        notificationID = 1000;
        int notificationID = spf.getInt("notificationID", 1000);
        //实例化通知管理器
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //设置通知标题
        builder.setContentTitle("ID:" + hostId);
        //设置通知内容
        builder.setContentText(text);
        //设置通知的方式，震动、LED灯、音乐等
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        //点击通知后，状态栏自动删除通知
        builder.setAutoCancel(true);
        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(PendingIntent.getActivity(this, notificationID + 1, new Intent(this, MainActivity.class), 0));//设置点击通知后将要启动的程序组件对应的PendingIntent
        Notification notification = builder.build();

        //发送通知
        notificationManager.notify(notificationID, notification);
        notificationID++;
        if (notificationID >= 2000) {
            spf.putInt("notificationID", 1000);
        } else {
            spf.putInt("notificationID", notificationID);
        }

    }


    /**
     * 倒计时
     */
    public void isStartTimer() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    handler.sendEmptyMessage(TIMER);
                }
            }, 1000, 1000);
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(DAEMON_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

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
                if (isWifiClose) {
                    Log.i(TAG, "onReceive:" + "有网络");
                    handler.sendEmptyMessageDelayed(LOG_OUT, 300);
                }
                isWifiClose = false;
            }

            @Override
            public void wifiClose() {
                Log.i("haha", "wifiClose");
            }

            @Override
            public void notConnected() {
                Log.i("haha", "notConnected");
                Log.i(TAG, "onReceive:" + "没有网络");
                isWifiClose = true;
            }

            @Override
            public void connected(ConnectivityManager manager, NetworkInfo networkInfo) {
                Log.i("haha", "connected");
                if (isWifiClose) {
                    Log.i(TAG, "onReceive:" + "有网络");
                    handler.sendEmptyMessageDelayed(LOG_OUT, 300);
                }
                isWifiClose = false;
            }
        });
    }

}
