package com.luuuzi.simplehttp.net.client

import android.content.Context
import android.util.Log
import com.luuuzi.simplehttp.app.App
import com.luuuzi.simplehttp.app.ConfigType
import com.luuuzi.simplehttp.net.callback.*
import com.luuuzi.simplehttp.net.error.ApiException
import com.luuuzi.simplehttp.widget.loader.LoaderStyle
import com.luuuzi.simplehttp.widget.loader.VCyunLoader
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:12
 *    desc   :
 */
class BaseObserver<T>(
    var context: Context?,
    var iFailure: IFailure?,
    var iSuccess: ISuccess<T>,
    var iRequest: IRequest?,
    var loaderStyle: LoaderStyle?,
    var iStatusView: IStatusView?,
    var iError: IError?
) : Observer<T> {
    private val tag: String = javaClass.simpleName
    var iNetError: INetError? = null
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(value: T) {
        Log.i(tag, "成功:$value")
        stopLoading();

        this.iStatusView?.openSuccessView()//隐藏加载view

        this.iSuccess.success(value)
        //请求结束
        iRequest?.onRequestEnd()
    }

    override fun onError(e: Throwable) {
        Log.i(tag, "错误:" + e.message)
        if (context != null)
            return
        if (iError != null) {
            if (e is ApiException) {
                iError?.onError(e.errorCode, e.message)
            } else {
                //其他错误
            }
        }
        /**
         * 通过反射创建异常处理类
         */
        try {
            var netErrotHandler: Any? = App.getConfiguration(
                ConfigType.NET_ERROR_HADNLE
            )
            if (netErrotHandler != null) {
                val c: Class<*> = netErrotHandler as Class<*>
                iNetError = c.newInstance() as INetError
                iNetError?.setThrowable(e, iStatusView)
            }
        } catch (e: Exception) {

        }

        iFailure?.onFailure(e.message)//请求失败

        iRequest?.onRequestEnd()//请求结束
        stopLoading()

    }
    private fun stopLoading(){
        if(loaderStyle!=null){
            VCyunLoader.stopLoading()
        }
    }
}