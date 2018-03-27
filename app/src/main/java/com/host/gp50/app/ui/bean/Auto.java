package com.host.gp50.app.ui.bean;

/**
 * com.host.gp50.app.ui.bean
 *
 * @author Administrator
 * @date 2018/01/09
 */

public class Auto {
    Long id;

    /**
     * 提示计划别名
     */
    String alias;

    /**
     * 主机ID
     */
    String hostId;

    /**
     * 加锁时
     */
    String lockHour;

    /**
     * 加锁分
     */
    String lockMin;

    /**
     * 解锁时
     */
    String unLockHour;

    /**
     * 解锁分
     */
    String unLockMin;

    /**
     * 是否打开
     */
    boolean isOpen;

    /**
     * 星期一
     */
    boolean isMonday;

    /**
     * 星期二
     */
    boolean isTuesday;

    /**
     * 星期三
     */
    boolean isWednesday;

    /**
     * 星期四
     */
    boolean isThursday;

    /**
     * 星期五
     */
    boolean isFriday;

    /**
     * 星期六
     */
    boolean isSaturday;

    /**
     * 星期日
     */
    boolean isSunday;
}
