package com.luuuzi.httplibrary


import android.app.Application
import com.luuuzi.common.app.App
import com.luuuzi.common.app.Configurator
import com.luuuzi.common.util.thread.ThreadUtils
import com.orhanobut.logger.Logger


/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 17:21
 *    desc   :
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        App.init(this)
        initThreadService()
    }

    private fun initThreadService() {
        ThreadUtils.executeByCpu(object : ThreadUtils.SimpleTask<Void>() {
            override fun doInBackground(): Void? {
                Configurator.getInstance()
                    .withApiHost("http://fanyi.youdao.com") //baseurl
//                    .withInterceptor(StethoInterceptor()) //google第三方拦截器
//                    .withInterceptor(ResponseDecryptInercept())//自定义拦截器
//                    .withNetErrorHandle(ErrorHandle::class.java) //添加错误返回处理
                    .configure()
                return null
            }

            override fun onSuccess(result: Void?) {
                Logger.t("MainApplication").d("初始完成")
            }

            override fun onFail(t: Throwable?) {
                Logger.t("MainApplication").e("初始化失败${t.toString()}")
            }

        })
    }
}