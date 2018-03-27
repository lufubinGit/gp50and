package com.host.gp50.app.utils;

import android.content.Context;
import android.widget.Toast;

import com.host.gp50.app.R;
/**
 * com.host.gp50.app.utils
 *
 * @author Administrator
 * @date 2017/11/28
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 强大的吐司，能够连续弹的吐司
     *
     * @param text 显示内容
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果不为空，则直接改变当前toast的文本
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 提示错误码信息
     *
     * @param context   上下文
     * @param errorCode 错误码
     */
    public static void showErrorCode(Context context, String errorCode) {
        switch (errorCode) {
            case "001":
                showToast(context,context.getString(R.string.ErrorCode01));
                break;
            case "002":
                showToast(context,context.getString(R.string.ErrorCode02));
                break;
            case "003":
                showToast(context,context.getString(R.string.ErrorCode03));
                break;
            case "004":
                showToast(context,context.getString(R.string.ErrorCode04));
                break;
            case "005":
                showToast(context,context.getString(R.string.ErrorCode05));
                break;
            case "006":
                showToast(context,context.getString(R.string.ErrorCode06));
                break;
            case "007":
                showToast(context,context.getString(R.string.ErrorCode07));
                break;
            case "008":
                showToast(context,context.getString(R.string.ErrorCode08));
                break;
            case "009":
                showToast(context,context.getString(R.string.ErrorCode09));
                break;
            case "010":
                showToast(context,context.getString(R.string.ErrorCode10));
                break;
            case "011":
                showToast(context,context.getString(R.string.ErrorCode11));
                break;
            case "012":
                showToast(context,context.getString(R.string.ErrorCode12));
                break;
            case "013":
                showToast(context,context.getString(R.string.ErrorCode13));
                break;
            default:
                showToast(context,"ErrorCode:" + errorCode);
                break;
        }
    }
}
