package com.luuuzi.common.app;


import com.luuuzi.common.util.sharepreference.VCPreference;

/**
 * 用户登录状态
 */
public class AccountManager {

    //全局保存token位置
    public static String userToken = null;


    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {
        VCPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    //判断是否登录
    public static boolean isSignIn() {
        return VCPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }


    private enum SignTag {
        SIGN_TAG
    }
}
