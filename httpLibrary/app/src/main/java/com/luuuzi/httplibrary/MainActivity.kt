package com.luuuzi.httplibrary

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.luuuzi.common.net.bean.BaseBean
import com.luuuzi.common.net.callback.IFailure
import com.luuuzi.common.net.callback.IRequest
import com.luuuzi.common.net.callback.ISuccess
import com.luuuzi.common.net.client.HttpClient
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

class MainActivity : RxAppCompatActivity() {
    private var tag: String = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HttpClient.Builder()
            .url("/openapi.do")
            .params("keyfrom", "Yanzhikai")
            .params("key", "2032414398")
            .params("type", "data")
            .params("doctype", "json")
            .params("version", "1.1")
            .params("q", "car")
            .request(object : IRequest {
                override fun onRequestStart() {//请求开始

                }

                override fun onRequestEnd() {//请求结束

                }

            })
            .failure(object : IFailure {
                override fun onFailure(errorMsg: String?) {//请求错误
                    Log.i(tag, "失败：$errorMsg")
                }
            })
            .setLifecycleTransformer(bindUntilEvent<Activity>(ActivityEvent.DESTROY))
            .build()
            .get()
            .request(object : ISuccess<Any> {
                //无参数写法
                override fun success(t: Any) {
                    Log.i(tag, "成功：$t ")
                }
            })
//            .request(BaseBean::class.java, object : ISuccess<BaseBean> {
//                //有参数写法
//                override fun success(t: BaseBean) {
//                    Log.i(tag, "成功：${t.code} ,${t.message}")
//                }
//
//            })
    }
}
