package com.host.gp50.app;

/**
 * com.host.gp20.gprs.app
 *
 * @author Administrator
 * @date 2017/12/08
 */

public interface Urls {
    //长连接最前面加个下划线 _   ,最后面加 $\r

    String request_header = "access-token";
    String access_token = "46e2c2da7a854bbeaeae99f60b6f4953";
    String server = "http://119.23.233.134";

    /**
     * 获取socket的IP
     */
    String GET_SOCKET_IP = server + "";

    /**
     * 获取100条sn
     */
    String GET_SN = server + "";

    /**
     * 主机历史记录
     */
    String HOST_HISTORY = server + "/app/alarmHistory";

    /**
     * 原文推送
     */
    String PUSH_MESSAGE = server + "";

    /**
     * 登录
     */
    String LOGIN = server + "/app/login";
    /**
     * 退出登录
     */
    String LOG_OUT = server + "/app/logout";

    /**
     * 注册
     */
    String REGISTER = server + "/app/register";

    /**
     * 绑定主机
     */
    String BIND_HOST = server + "/app/addDevice";

    /**
     * 编辑主机
     */
    String EDIT_HOST = server + "/app/editDevice";

    /**
     * 主机布撤防
     */
    String OPT_HOST = server + "/app/optDevice";

    /**
     * 查询用户绑定的主机
     */
    String QUERY_BIND_HOST = server + "/app/queryDeviceList";

    /**
     * 解除主机主用户的绑定
     */
    String UNBIND_DEVICE = server + "/app/unbindDevice";

    /**
     * 修改用户信息
     */
    String EDIT_USER_INFO = server + "/app/editUserInfo";

    /**
     * 分享设备
     */
    String SHARE_DEVICE = server + "/app/shareDevice";

    /**
     * 查询主机子用户
     */
    String QUERY_DEVICE_SUBUSER = server + "/app/queryDeviceSubUser";
}
