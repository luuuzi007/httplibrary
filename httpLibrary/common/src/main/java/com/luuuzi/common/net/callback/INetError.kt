package com.luuuzi.common.net.callback

import com.luuuzi.common.net.client.IStatusView

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:02
 *    desc   : 可通过该接口从外面注入异常处理类
 */
interface INetError {
    fun setThrowable(e:Throwable,statusView:IStatusView?)
}