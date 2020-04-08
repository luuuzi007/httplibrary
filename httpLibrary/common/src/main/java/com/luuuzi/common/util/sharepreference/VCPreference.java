package com.luuuzi.common.util.sharepreference;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;


import com.luuuzi.common.app.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 基础SharedPreferences类
 */
public class VCPreference {


    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(App.getApplicationContext());
    private static final String APP_PREFERENCES_KEY = "PROFILE";


    private static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }

    private static SharedPreferences.Editor getEditor() {
        return PREFERENCES.edit();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCES_KEY, null);
    }

    public static void setAppProfile(String val) {
        getEditor()
                .putString(APP_PREFERENCES_KEY, val)
                .apply();
    }

    public static void removeAppProfile() {
        getEditor()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public static void remove(String key) {
        getEditor()
                .remove(key)
                .apply();

    }


    public static void clearAppPreferences() {
        getEditor()
                .clear()
                .apply();
    }

    public static void setAppFlag(String key, boolean flag) {
        getEditor()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }

    public static void addCustomAppProfile(String key, String val) {
        getEditor()
                .putString(key, val)
                .apply();
    }

    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }


    /**
     * 添加参数
     *
     * @param key
     * @param value
     */
    public static void put(final String key, final Object value) {
        // 创建字节输出流
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(value);
            baos.close();
            oos.close();
            // 将字节流编码成base64的字符窜
            final String Base64Str = new String(Base64.encode(baos.toByteArray(), 1));
            getEditor()
                    .putString(key, Base64Str)
                    .apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数
     *
     * @param key
     * @return 返回一个泛型对象，当不存在该对象则返回null值, 当对象为简单数据类型时必须使用其封装类
     */
    public static <T> T get(final String key) {
        T value = null;
        final String productBase64 = getAppPreference().getString(key, null);
        if (productBase64 == null) {
            return null;
        }
        //读取字节
        final byte[] base64 = Base64.decode(productBase64.getBytes(), 1);

        //封装到字节流
        final ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //读取对象
            final ObjectInputStream bis = new ObjectInputStream(bais);
            value = (T) bis.readObject();
            bais.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


}
