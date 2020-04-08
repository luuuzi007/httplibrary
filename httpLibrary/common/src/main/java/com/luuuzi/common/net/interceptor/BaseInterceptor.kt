package com.luuuzi.common.net.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import java.util.*

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 16:17
 *    desc   :基础拦截器，可直接获取拦截器中的某些信息
 */
abstract class BaseInterceptor :Interceptor {

    protected open fun getUrlParameters(chain: Interceptor.Chain): LinkedHashMap<String, String>? {
        val parameters =
            LinkedHashMap<String, String>()
        val request = chain.request()
        val httpUrl = request.url()
        for (i in 0 until httpUrl.querySize()) {
            parameters[httpUrl.queryParameterName(i)] = httpUrl.queryParameterValue(i)
        }
        return parameters
    }


    protected open fun getUrlParameters(
        chain: Interceptor.Chain,
        key: String?
    ): String? {
        val request = chain.request()
        return request.url().queryParameter(key)
    }


    protected open fun getBodyParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val parameters =
            LinkedHashMap<String, String>()
        val request = chain.request()
        val body = request.body() as FormBody?
        if (body != null) {
            for (i in 0 until body.size()) {
                parameters[body.name(i)] = body.value(i)
            }
        }
        return parameters
    }

    protected open fun getBodyParameters(
        chain: Interceptor.Chain,
        key: String?
    ): String? {
        return getBodyParameters(chain)[key]
    }
}