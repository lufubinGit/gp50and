package com.host.gp50.app.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * com.host.gp50.app.ui.bean
 *
 * @author Administrator
 * @date 2017/12/06
 */
@Entity
public class Host implements Serializable{
    static final long serialVersionUID = 42L;
    @Id(autoincrement = true)
    Long id;
    /**
     * 用户ID
     */
    String userId;
    /**
     * 主机别名
     */
    String alias;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * 主机服务商名称
     */
    String hostServiceProvider;

    /**
     * 主机服务商ID
     */
    String hostServiceProviderId;

    /**
     * 主机地址
     */
    String hostAddress;

    /**
     * 主机固件版本
     */
    String hostVersion;

    /**
     * 主机IP地址
     */
    String hostIp;

    /**
     * 主机类型
     */
    int hostType;

    /**
     * 主机是否在线
     */
    @Transient
    boolean isOnline;

    /**
     * 主机信号
     */
    int signal;

    /**
     * 主机布防状态
     */
    int armState;

    /**
     * 是否故障
     */
    @Transient
    boolean isError;

    /**
     * 主机故障状态码
     */
    String errorCode;

    /**
     * 主机是否报警
     */
    @Transient
    boolean isAlarm;

    /**
     * 主机报警状态码
     */
    String alarmCode;

    /**
     * 主机是否在学码
     */
    @Transient
    boolean isStudyCode;

    /**
     * 主机用户数
     */
    int hostUserNumber;

    /**
     * 主机子设备数量
     */
    int subNumber;

    /**
     * 纬度
     */
    Double latitude;

    /**
     * 经度
     */
    Double longitude;

    /**
     * 是否是主用户
     */
    boolean isMainUser;

    @Generated(hash = 1280169439)
    public Host(Long id, String userId, String alias, String hostId,
            String hostServiceProvider, String hostServiceProviderId,
            String hostAddress, String hostVersion, String hostIp, int hostType,
            int signal, int armState, String errorCode, String alarmCode,
            int hostUserNumber, int subNumber, Double latitude, Double longitude,
            boolean isMainUser) {
        this.id = id;
        this.userId = userId;
        this.alias = alias;
        this.hostId = hostId;
        this.hostServiceProvider = hostServiceProvider;
        this.hostServiceProviderId = hostServiceProviderId;
        this.hostAddress = hostAddress;
        this.hostVersion = hostVersion;
        this.hostIp = hostIp;
        this.hostType = hostType;
        this.signal = signal;
        this.armState = armState;
        this.errorCode = errorCode;
        this.alarmCode = alarmCode;
        this.hostUserNumber = hostUserNumber;
        this.subNumber = subNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isMainUser = isMainUser;
    }

    @Generated(hash = 1087452422)
    public Host() {
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getSubNumber() {
        return this.subNumber;
    }

    public void setSubNumber(int subNumber) {
        this.subNumber = subNumber;
    }

    public int getHostUserNumber() {
        return this.hostUserNumber;
    }

    public void setHostUserNumber(int hostUserNumber) {
        this.hostUserNumber = hostUserNumber;
    }

    public boolean getIsStudyCode() {
        return this.isStudyCode;
    }

    public void setIsStudyCode(boolean isStudyCode) {
        this.isStudyCode = isStudyCode;
    }

    public String getAlarmCode() {
        return this.alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public boolean getIsAlarm() {
        return this.isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getArmState() {
        return this.armState;
    }

    public void setArmState(int armState) {
        this.armState = armState;
    }

    public int getSignal() {
        return this.signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public boolean getIsOnline() {
        return this.isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public int getHostType() {
        return this.hostType;
    }

    public void setHostType(int hostType) {
        this.hostType = hostType;
    }

    public String getHostIp() {
        return this.hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostVersion() {
        return this.hostVersion;
    }

    public void setHostVersion(String hostVersion) {
        this.hostVersion = hostVersion;
    }

    public String getHostAddress() {
        return this.hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getHostServiceProvider() {
        return this.hostServiceProvider;
    }

    public void setHostServiceProvider(String hostServiceProvider) {
        this.hostServiceProvider = hostServiceProvider;
    }

    public String getHostId() {
        return this.hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getHostServiceProviderId() {
        return this.hostServiceProviderId;
    }

    public void setHostServiceProviderId(String hostServiceProviderId) {
        this.hostServiceProviderId = hostServiceProviderId;
    }

    public boolean getIsMainUser() {
        return this.isMainUser;
    }

    public void setIsMainUser(boolean isMainUser) {
        this.isMainUser = isMainUser;
    }
}
