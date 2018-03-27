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
public class Phone implements Serializable{
    static final long serialVersionUID = 42L;
    @Id(autoincrement = true)
    Long id;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * 语音电话别名
     */
    String alias;

    /**
     * 语音电话号码
     */
    String phoneNumber;

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Generated(hash = 234982846)
    public Phone(Long id, String hostId, String alias, String phoneNumber) {
        this.id = id;
        this.hostId = hostId;
        this.alias = alias;
        this.phoneNumber = phoneNumber;
    }

    @Generated(hash = 429398894)
    public Phone() {
    }


}
