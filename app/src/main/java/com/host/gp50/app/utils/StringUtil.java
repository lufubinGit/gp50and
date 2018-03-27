package com.host.gp50.app.utils;

import android.content.Context;

import com.host.gp50.app.R;

/**
 * com.host.gp50.app.utils
 *
 * @author Administrator
 * @date 2018/02/07
 */

public class StringUtil {

    /**
     * 解析CID事件
     *
     * @param context  上下文
     * @param cidType  CID type
     * @param cidvalue CID码
     * @param depart   分区号
     * @param optcode  防区号或者操作者编号
     * @return 事件
     */
    public static String resolveCid(Context context, String cidType, String cidvalue, String depart, int optcode) {
        String result = "";
        switch (cidvalue) {
            case "134":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code01) ;
                break;
            case "132":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code02);
                break;
            case "131":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code03);
                break;
            case "100":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code04);
                break;
            case "110":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code05);
                break;
            case "151":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code06);
                break;
            case "137":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code07);
                break;
            case "121":
                result = context.getString(R.string.zone) + optcode + ":" +
                        context.getString(R.string.cid_code08);
                break;
            case "301":
                if (optcode != 0) {
                    result = context.getString(R.string.zone) + optcode + ":" +
                            context.getString(R.string.cid_code09) + "," + resolveOpt(context, optcode);
                } else {
                    result = resolveOpt(context, optcode);
                }
                break;
            case "302":
                if (optcode != 0) {
                    result = context.getString(R.string.zone) + optcode + ":" +
                            context.getString(R.string.cid_code09) + "," + resolveOpt(context, optcode);
                } else {
                    result = resolveOpt(context, optcode);
                }
                break;
            case "381":
                result = context.getString(R.string.cid_code12);
                break;
            case "401":
                if (cidType.equals("3")) {
                    result = context.getString(R.string.arm_away) + "," + resolveOpt(context, optcode);
                } else {
                    result = context.getString(R.string.dis_arm) + "," + resolveOpt(context, optcode);
                }
                break;
            case "441":
                result = context.getString(R.string.arm_stay) + "," + resolveOpt(context, optcode);
                break;
            case "455":
                result = context.getString(R.string.cid_code13);
                break;
            case "570":
                if (optcode != 0) {
                    result = context.getString(R.string.zone) + optcode + ":" +
                            context.getString(R.string.cid_code14);
                } else {
                    result = context.getString(R.string.cid_code14);
                }
                break;
            case "602":
                result = context.getString(R.string.cid_code15);
                break;
            case "627":
                if (cidType.equals("3")) {
                    result = context.getString(R.string.cid_code17);
                } else {
                    result = context.getString(R.string.cid_code16);
                }
                if (optcode != 0) {
                    result += "," + resolveOpt(context, optcode);
                }
                break;
            case "351":
                result = context.getString(R.string.cid_code18);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 解析使用者编号
     *
     * @param context 上下文
     * @param i       使用者编号
     * @return 字符串
     */
    public static String resolveOpt(Context context, int i) {
        String result = "";
        switch (i) {
            //主机操作
            case 0:
                result = context.getString(R.string.opt_num00);
                break;
            //主用户密码操作
            case 1:
                result = context.getString(R.string.opt_num01);
                break;
            //编程密码操作
            case 2:
                result = context.getString(R.string.opt_num02);
                break;
            //挟持密码操作
            case 3:
                result = context.getString(R.string.opt_num03);
                break;
            //临时密码操作
            case 4:
                result = context.getString(R.string.opt_num04);
                break;
            //用户
            case 5:
            case 6:
            case 7:
            case 8:
                String s1 = context.getString(R.string.opt_num05_08);
                result = String.format(s1, i - 4);
                break;
            //遥控器
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                String s2 = context.getString(R.string.opt_num11_18);
                result = String.format(s2, i - 10);
                break;
            //电话
            case 21:
            case 22:
            case 23:
            case 24:
                String s3 = context.getString(R.string.opt_num11_18);
                result = String.format(s3, i - 20);
                break;
            //自动布撤防操作
            case 30:
                result = context.getString(R.string.opt_num30);
                break;
            //APP操作
            case 50:
                result = context.getString(R.string.opt_num50);
                break;
            default:
                break;
        }
        return result;
    }
}
