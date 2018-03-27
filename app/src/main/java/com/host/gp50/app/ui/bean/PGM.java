package com.host.gp50.app.ui.bean;

/**
 * com.host.gp50.app.ui.bean
 *
 * @author Administrator
 * @date 2017/12/07
 */

public class PGM {

    Long id;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * PGM别名
     */
    String ailas;

    /**
     * 是否打开
     */
    boolean isOpen;

    /**
     * 是否外出布防
     */
    boolean isArmAway;

    /**
     * 是否留守布防
     */
    boolean isArmStay;

    /**
     * 是否撤防
     */
    boolean isDisArm;

    /**
     * 是否警号跟随
     */
    boolean isFollow;
}
