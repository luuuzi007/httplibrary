package com.luuuzi.common.util.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.luuuzi.common.app.App;


/**
 * Toast工具类
 *
 * @author WikerYong   Email:<a href="#">yw_312@foxmail.com</a>
 * @version 2012-5-21 下午9:21:01
 */
public class ToastUtil {
    /**
     * 无资源
     */
    public static int RESOURCE_NONE = -1;
    /**
     * 错误提示
     */
    public static int ERROR = 1;
    /**
     * 警示提示
     */
    public static int HINT = 2;
    /**
     * 成功提示
     */
    public static int SUCCESS = 3;
    private static Toast sToast;
    //    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore = true;
    private static Toast mToast;

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showToastCanCancel(String message) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getApplicationContext(), null, Toast.LENGTH_SHORT);
            //解决小米手机显示应用名
            sToast.setText(message);

            sToast.show();
        } else {
            mToast.cancel();
            mToast = Toast.makeText(App.getApplicationContext(), null, Toast.LENGTH_SHORT);
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static void init(boolean isJumpWhenMore) {
        ToastUtil.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToastSafe(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToastSafe(@StringRes int resId, Object... args) {

        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToastSafe(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToastSafe(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToastSafe(@StringRes int resId) {

        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToastSafe(@StringRes int resId, Object... args) {

        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToastSafe(String format, Object... args) {

        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongToast(CharSequence text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongToast(@StringRes int resId) {
        showToast(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongToast(@StringRes int resId, Object... args) {
        showToast(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongToast(String format, Object... args) {
        showToast(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void showToast(@StringRes int resId, int duration) {
        showToast(App.getApplicationContext().getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(@StringRes int resId, int duration, Object... args) {
        showToast(String.format(App.getApplicationContext().getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void showToast(String format, int duration, Object... args) {
        showToast(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     * 注意：在非主线程中进行Toast显示，单独创建Loop对象会造成Thread对象无法销毁，增加内存泄漏的风险
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void showToast(CharSequence text, int duration) {
//        if (isJumpWhenMore) cancelToast();
//        if (sToast == null) {
//            try {
//                if (Looper.myLooper() == null)
//                    Looper.prepare();
//                sToast = Toast.makeText(XiniuApplication.instance(), text.toString(), duration);
//                sToast.show();
//                if (Looper.myLooper() == null)
//                    Looper.loop();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
////            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                sToast = ToastCompat.makeText(App.getApplicationContext(), text.toString(), duration);
////            } else {
////                sToast = SystemToast.makeText(App.getApplicationContext(), text.toString(), duration);
////            }
////            sToast.setGravity(Gravity.BOTTOM, 0, DisplayUtil.dip2px(App.getApplicationContext(), 80));
//        } else {
//            sToast.setText(text.toString());
//            sToast.show();
////            sToast.setDuration(duration);
//        }

        if (isJumpWhenMore) {
            cancelToast();
        }
        //在主线程
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (sToast == null) {
                //解决小米手机显示应用名
                sToast = Toast.makeText(App.getApplicationContext(),null, duration);
                sToast.setText(text.toString());

                sToast.show();
            } else {
                sToast.setText(text.toString());
                sToast.show();
            }
            //非主线程
        } else {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new MyRunable(text, duration));
        }
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     */
    public static void showMessage(Context context, String text) {
        //今日头条适配
//        if (QMUIDeviceHelper.isTablet(App.getApplicationContext())) {//是平板
//            AutoSize.autoConvertDensityOfGlobal(App.getConfiguration(ConfigType.ACTIVITY));
//        }
        showToast(text);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    public static void showMessage(Context context, String text, int duration) {
        showToast(text, duration);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     */
    public static void showToast(Context context, String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     */
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示吐司
     *
     * @param text 文本
     */
    public static void showMessage(String text) {
        showToast(text);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    public static void showToast(Context context, String text, int duration) {
        showToast(text, duration);
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    private static class MyRunable implements Runnable {
        private CharSequence text;
        private int time;

        public MyRunable(CharSequence text, int time) {
            this.text = text;
            this.time = time;
        }

        @Override
        public void run() {
//            Toast.makeText(App.getApplicationContext(), text, time).show();
            //解决小米手机显示应用名
            Toast toast = Toast.makeText(App.getApplicationContext(), null, time);
            toast.setText(text);
            toast.show();

        }
    }
}