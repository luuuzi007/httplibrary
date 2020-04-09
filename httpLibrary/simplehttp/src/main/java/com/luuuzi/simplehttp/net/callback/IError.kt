package com.luuuzi.simplehttp.net.callback

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:00
 *    desc   : 请求错误回调
 */
interface IError {
    fun onError(code:Int,msg:String)
}