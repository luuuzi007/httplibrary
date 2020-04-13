package com.luuuzi.httplibrary


import android.app.Application
import com.luuuzi.httplibrary.inercept.ResponseDecryptInercept
import com.luuuzi.simplehttp.app.App
import com.luuuzi.simplehttp.app.Configurator
import com.luuuzi.simplehttp.util.log.MLog
import com.luuuzi.simplehttp.util.thread.ThreadUtils



/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 17:21
 *    desc   :
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(this)//必填：保存全局context
        initThreadService()
    }

    private fun initThreadService() {
        ThreadUtils.executeByCpu(object : ThreadUtils.SimpleTask<Void>() {
            override fun doInBackground(): Void? {
                Configurator.getInstance()
                    .withApiHost("http://fanyi.youdao.com") //必填：baseurl
//                    .withInterceptor(ResponseDecryptInercept())//可选：自定义拦截器(这里自己添加拦截器去设置head等)
//                    .withNetErrorHandle(ErrorHandle::class.java) //可选：添加错误返回处理
                    .configure()//是否初始化完成
                return null
            }

            override fun onSuccess(result: Void?) {
                MLog.i("初始完成")
            }

            override fun onFail(t: Throwable?) {
                MLog.e("初始化失败${t.toString()}")
            }

        })
    }
}