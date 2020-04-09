package com.luuuzi.simplehttp.net.callback

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:01
 *    desc   : 请求失败回调
 */
interface IFailure {
    fun onFailure(errorMsg:String?)
}