package com.luuuzi.httplibrary.inercept

import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import com.google.gson.Gson
import com.luuuzi.httplibrary.BuildConfig
import com.luuuzi.simplehttp.app.AccountManager
import com.luuuzi.simplehttp.app.App
import com.luuuzi.simplehttp.net.HttpResultCode
import com.luuuzi.httplibrary.bean.BaseBean
import com.luuuzi.simplehttp.net.error.ApiException
import com.luuuzi.simplehttp.net.interceptor.BaseInterceptor
import com.luuuzi.simplehttp.util.log.MLog
import com.luuuzi.simplehttp.util.toast.ToastUtil
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 16:20
 *    desc   : 自定义拦截器(测试用)
 */
class ResponseDecryptInercept : BaseInterceptor() {
    private val TAG = "ResponseDecryptInercept"
    private val UTF8 = StandardCharsets.UTF_8
    override fun intercept(chain: Interceptor.Chain): Response {
        val req =
            chain.request().newBuilder()
//                .removeHeader("User-Agent")
//                .addHeader("User-Agent", getUserAgent())
//                .addHeader("token", if (AccountManager.userToken == null) "" else AccountManager.userToken)
                .addHeader("loginType", "LOGIN_APP_PASSWORD")
                .build()
        //打印请求内容
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "url:" + chain.request().url().toString())
            Log.e(TAG, "token:" + AccountManager.userToken)
        }
        val requestBody = req.body() //请求内容

        if (requestBody != null && BuildConfig.DEBUG) {
            val connection = chain.connection()
            val protocol =
                if (connection != null) connection.protocol() else Protocol.HTTP_1_1
            val requestStartMessage = "--> " + req.method() + ' ' + req.url() + ' ' + protocol
            Log.e(TAG, "url:$requestStartMessage")
            Log.e(TAG, "Content-Type: " + requestBody.contentType())
            Log.e(TAG, "Content-Length: " + requestBody.contentLength())
            Log.e(TAG, "requestBody:" + requestBodyToString(requestBody))
        }

        var rep = chain.proceed(req)

        val responseBody = rep.body()
        val source = responseBody!!.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.

        val buffer = source.buffer()

        //处理返回结果
        val json = buffer.clone().readString(UTF8)

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "json--->>:$json")
//            MLog.e(rep.request().url().toString() + "&is_not_encrypt=1&is_api_test=1")
            MLog.json(MLog.E, json)
        }
        val data: BaseBean = Gson().fromJson<BaseBean>(json, BaseBean::class.java)
        if (data.code != HttpResultCode.REQUEST_SUCCESS) {
//            if (data.getCode() === HttpResultCode.TOKEN_PAST_DUE) {
//                ToastUtil.showMessage("请重新登录")
//                EventBus.getDefault().post(EventBusBean(EventBusBean.FLAG_7))
//            } else {
            ToastUtil.showMessage(data.message)
//            }
            throw ApiException(data.code, data.message)
        }

        rep = rep.newBuilder()
            .body(ResponseBody.create(responseBody.contentType(), json))
            .build()

        return rep
    }

    //输出body
    @Throws(IOException::class)
    fun requestBodyToString(requestBody: RequestBody): String? {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }

    /**
     * 代理设置
     */
    private fun getUserAgent(): String? {
        var userAgent = ""
        userAgent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                WebSettings.getDefaultUserAgent(App.getApplicationContext())
            } catch (e: Exception) {
                System.getProperty("http.agent")
            }
        } else {
            System.getProperty("http.agent")
        }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}