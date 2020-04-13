package com.luuuzi.simplehttp.widget.loader;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDialog;

import com.luuuzi.common.R;
import com.luuuzi.simplehttp.app.App;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * 用于全局调用等待dialog
 */
public class VCyunLoader {

    //    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_SIZE_SCALE = 7;
    private static final int LOADER_OFFSET_SCALE = 10;

    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoadingNocancel(Context context, Enum<LoaderStyle> type) {
        showLoadingNocancel(context, type.name());
    }

    /**
     * 在使用的时候要设置activity主题为noactionbar
     *
     * @param context 上下文
     * @param type    dialog类型
     */
    public static void showLoading(Context context, String type) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        avLoadingIndicatorView.setPadding(dp2px(context, 6),
                dp2px(context, 6),
                dp2px(context, 6),
                dp2px(context, 6));

        dialog.setContentView(avLoadingIndicatorView);
//        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失

        int deviceWidth = getScreenWidth();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceWidth / LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
            dialogWindow.setBackgroundDrawableResource(R.drawable.shape_vcyun_loader);
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * @param context
     * @param type
     */
    public static void showLoadingNocancel(Context context, String type) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        avLoadingIndicatorView.setPadding(dp2px(context, 6),
                dp2px(context, 6),
                dp2px(context, 6),
                dp2px(context, 6));

        dialog.setContentView(avLoadingIndicatorView);
        dialog.setCancelable(false);

        int deviceWidth = getScreenWidth();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceWidth / LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
            dialogWindow.setBackgroundDrawableResource(R.drawable.shape_vcyun_loader);
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getApplicationContext().getResources().getDisplayMetrics());
    }

    public static int getScreenWidth() {
        final Resources resources = App.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}
