package com.host.gp50.app;

/**
 * com.host.gp50.app
 *
 * @author Administrator
 * @date 2017/12/07
 */

public interface Api {

    int DIS_ARM = 3;
    int ARM_STAY = 2;
    int ARM_AWAY = 1;
    int FAIL = 96;
    int SUCCESS = 97;
    int ERROR = 98;

    String selectHost = "selectHost";
    String RENAME = "RENAME";
    String ADDRESS = "ADDRESS";
    String SERVICE = "SERVICE";
    String LONGITUDE = "Longitude";
    String LATITUDE = "latitude";
}
