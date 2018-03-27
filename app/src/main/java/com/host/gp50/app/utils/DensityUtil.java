package com.host.gp50.app.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * com.host.gp50.app.utils
 *
 * @author Administrator
 * @date 2017/11/28
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置控件的宽高
     * @param view      控件
     * @param height    高度
     * @param width     宽度
     */
    public static void setViewLayoutParams(View view, int height, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置空间的Margin
     * @param v 控件
     * @param l 左边距
     * @param t 上边距
     * @param r 右边距
     * @param b 下边距
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
