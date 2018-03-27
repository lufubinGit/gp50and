package com.host.gp50.app.ui.bean;

/**
 * com.host.gp50.app.ui.bean
 *
 * @author Administrator
 * @date 2017/12/07
 */

public class Zone {

    Long id;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * 防区别名
     */
    String alias;

    /**
     * 防区序号
     */
    String zoneRank;

    /**
     * 防区ID
     */
    String zoneId;

    /**
     * 防区类型
     */
    String zoneType;

    /**
     * 是否报警
     */
//    @Transient
    boolean isAlarm;

    /**
     * 是否故障
     */
//    @Transient
    boolean isError;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getZoneRank() {
        return zoneRank;
    }

    public void setZoneRank(String zoneRank) {
        this.zoneRank = zoneRank;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
