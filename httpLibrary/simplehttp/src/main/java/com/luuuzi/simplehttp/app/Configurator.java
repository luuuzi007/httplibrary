package com.luuuzi.simplehttp.app;

import android.app.Activity;
import android.os.Handler;

import com.luuuzi.simplehttp.net.callback.INetError;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {


    private static final HashMap<Object, Object> VC_CONFIGS = new HashMap<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    private static final Handler HANDLER = new Handler();


    private Configurator() {
        VC_CONFIGS.put(ConfigType.CONFIG_READY, false);
        VC_CONFIGS.put(ConfigType.HANDLER, HANDLER);
    }

    public static Configurator getInstance() {
        return ConfiguratorHolder.INSTANCE;
    }

    final HashMap<Object, Object> getVCConfigs() {
        return VC_CONFIGS;
    }

    public final void configure() {
//        Logger.addLogAdapter(new AndroidLogAdapter());
        VC_CONFIGS.put(ConfigType.CONFIG_READY, true);

    }

    public final Configurator withApiHost(String apiHost) {
        VC_CONFIGS.put(ConfigType.API_HOST, apiHost);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        VC_CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        VC_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        VC_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        VC_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    //添加网络异常处理机制
    public final Configurator withNetErrorHandle(Class<? extends INetError> clazz) {
        VC_CONFIGS.put(ConfigType.NET_ERROR_HADNLE, clazz);
        return this;
    }


    public final Configurator withInterceptors(ArrayList<Interceptor> interceptor) {
        INTERCEPTORS.addAll(interceptor);
        VC_CONFIGS.put(ConfigType.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) VC_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("配置未完成！");
        }
    }

    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = VC_CONFIGS.get(key);
        //异常处理类和拦截器为非必须配置,可以为空
        if (key != ConfigType.NET_ERROR_HADNLE && key != ConfigType.INTERCEPTORS) {
            if (value == null) {
                throw new NullPointerException(key.toString() + " IS NULL");
            }
        }
        return (T) VC_CONFIGS.get(key);
    }

    private static class ConfiguratorHolder {
        private static final Configurator INSTANCE = new Configurator();
    }

}
