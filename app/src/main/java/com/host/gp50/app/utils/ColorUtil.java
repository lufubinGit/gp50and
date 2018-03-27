package com.host.gp50.app.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * com.host.gp50.app.utils
 *
 * @author Administrator
 * @date 2018/03/20
 */

public class ColorUtil {

    /**
     * 改变图片颜色
     * @param drawable  图片
     * @param colors    颜色
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
