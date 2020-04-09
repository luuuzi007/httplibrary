package com.luuuzi.simplehttp.net.error

import java.lang.RuntimeException

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 16:24
 *    desc   : 自定义异常
 */
public class ApiException(var errorCode: Int, var errorMessage: String) :
    RuntimeException(errorMessage)