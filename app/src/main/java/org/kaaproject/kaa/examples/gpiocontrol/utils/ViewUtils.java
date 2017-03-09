package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public final class ViewUtils {

    /**
     * Converts density independent points (DIP/DP) to pixels (PX).
     *
     * @param context context to extract display metrics
     * @param dp      value in density independent points
     * @return value in pixels
     */
    public static float dpToPx(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * Converts pixels (PX) to density independent points (DIP/DP).
     *
     * @param context context to extract display metrics
     * @param px      value in pixels
     * @return value in density independent points
     */
    public static float pxToDp(Context context, float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }

    public static RelativeLayout.LayoutParams getCommonLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }
}
