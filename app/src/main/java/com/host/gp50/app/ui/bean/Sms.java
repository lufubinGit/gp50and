package com.host.gp50.app.ui.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * com.host.gp50.app.ui.bean
 *
 * @author Administrator
 * @date 2017/12/07
 */
@Entity
public class Sms implements Serializable{
    static final long serialVersionUID = 42L;
    @Id(autoincrement = true)
    Long id;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * 短信别名
     */
    String alias;

    /**
     * 短信号码
     */
    String smsNumber;

    /**
     * 是否布撤防
     */
    boolean isArm;

    /**
     * 是否报警
     */
    boolean isAlarm;

    /**
     * 是否故障
     */
    boolean isError;

    public boolean getIsError() {
        return this.isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public boolean getIsAlarm() {
        return this.isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public boolean getIsArm() {
        return this.isArm;
    }

    public void setIsArm(boolean isArm) {
        this.isArm = isArm;
    }

    public String getSmsNumber() {
        return this.smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHostId() {
        return this.hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1396598880)
    public Sms(Long id, String hostId, String alias, String smsNumber,
            boolean isArm, boolean isAlarm, boolean isError) {
        this.id = id;
        this.hostId = hostId;
        this.alias = alias;
        this.smsNumber = smsNumber;
        this.isArm = isArm;
        this.isAlarm = isAlarm;
        this.isError = isError;
    }

    @Generated(hash = 172684796)
    public Sms() {
    }
}
