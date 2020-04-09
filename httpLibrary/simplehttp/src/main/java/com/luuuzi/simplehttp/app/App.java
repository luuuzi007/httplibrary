package com.luuuzi.simplehttp.app;


import android.content.Context;
import android.os.Handler;

/**
 * 用于配置App信息与获取核心库配置信息
 */
public final class App {

    /**
     * 初始化配置
     *
     * @param context
     * @return 保存数据的工具类
     */
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getVCConfigs().put(ConfigType.APPLICATION_CONTEXT, context);
        return Configurator.getInstance();
    }


    /**
     * 获取配置
     *
     * @return
     */
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }


    /**
     * 获取某个配置信息
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    /**
     * 获取配置信息中全局ApplicationContext
     *
     * @return
     */
    public static Context getApplicationContext() {
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    /**
     * 获取配置信息中请求域名
     *
     * @return
     */
    public static String getHttpHost() {
        return getConfiguration(ConfigType.API_HOST);
    }

    /**
     * 获取全局handler
     *
     * @return
     */
    public static Handler getHandler() {
        return getConfiguration(ConfigType.HANDLER);
    }


}
