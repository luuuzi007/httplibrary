package com.luuuzi.common.net.callback

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:11
 *    desc   :请求成功回调
 */
interface ISuccess<T> {
    fun success(t: T)
}