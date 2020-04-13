package com.luuuzi.httplibrary

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.luuuzi.simplehttp.net.callback.IError
import com.luuuzi.simplehttp.net.callback.IFailure
import com.luuuzi.simplehttp.net.callback.IRequest
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.net.client.HttpClient

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity(), View.OnClickListener {
    private var tag: String = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_tet.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_tet -> {
                HttpClient.Builder()
                    .url("/openapi.do")
                    .params("keyfrom", "Yanzhikai")
                    .params("key", "2032414398")
                    .params("type", "data")
                    .params("doctype", "json")
                    .params("version", "1.1")
                    .params("q", "car")
                    .loader(this)//显示加载dialog
//                    .loader(this,LoaderStyle.BallBeatIndicator)//加载dialog样式
                    .request(object : IRequest {
                        override fun onRequestStart() {//请求开始
                            Log.i(tag, "请求开始,一般用于显示自定义加载dialog")
                        }

                        override fun onRequestEnd() {//请求结束
                            Log.i(tag, "请求结束")
                        }
                    }).failure(object : IFailure {
                        override fun onFailure(errorMsg: String?) {//请求错误
                            Log.i(tag, "失败：$errorMsg")
                        }
                    }).error(object : IError {
                        override fun onError(code: Int, msg: String?) {
                            Log.i(tag, "错误：$msg")
                        }
                    }).setLifecycleTransformer(bindUntilEvent<Activity>(ActivityEvent.DESTROY))
                    .build()
                    .get()
                    .request(object : ISuccess<Any> {  //不需要返回结果写法
                        override fun success(t: Any) {
                            Log.i(tag, "成功：$t ")
                        }
                    })
//            .request(BaseBean::class.java, object : ISuccess<BaseBean> {//需要返回结果写法

//                override fun success(t: BaseBean) {
//                    Log.i(tag, "成功：${t.code} ,${t.message}")
//                }
//            })
            }
        }
    }
}
