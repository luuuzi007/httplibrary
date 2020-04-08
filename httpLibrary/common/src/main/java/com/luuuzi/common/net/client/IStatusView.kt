package com.luuuzi.common.net.client

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:08
 *    desc   :状态视图行为定义,可通过该接口从外部注入状态视图控件
 */
interface IStatusView {
    //加载中视图
    fun openLoadingView()

    //没有数据视图
    fun openDataEmptyView()

    //网络异常视图
    fun openNetErrorView()

    //没有网络视图
    fun openNoNetView()

    //服务器维护中
    fun serverMaintain()

    //加载成功
    fun openSuccessView()

    //没有权限访问
    fun openNoAuthority()
}