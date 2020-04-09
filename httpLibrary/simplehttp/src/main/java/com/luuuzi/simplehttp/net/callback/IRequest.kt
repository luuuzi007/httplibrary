package com.luuuzi.simplehttp.net.callback

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:10
 *    desc   :开始请求与结束请求回调
 */
interface IRequest {
    fun onRequestStart()
    fun onRequestEnd()
}