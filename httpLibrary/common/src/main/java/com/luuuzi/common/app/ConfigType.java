package com.luuuzi.common.app;

/**
 * 核心库可配置APP信息枚举类
 */
public enum ConfigType {
    API_HOST,//请求域名
    APPLICATION_CONTEXT,//可全局调用applicationContext
    CONFIG_READY,//是否初始化核心库完毕
    INTERCEPTORS,//注入okhttp拦截器
    HANDLER,//全局handler
    WE_CHAT_APP_ID,//可保存微信id
    WE_CHAT_APP_SECRET,//可保存微信secret
    ACTIVITY,//保存全局主activity
    NET_ERROR_HADNLE//注入网络异常处理类


}
