package com.luuuzi.common.net.client

import android.content.Context
import android.util.Log
import com.luuuzi.common.app.App
import com.luuuzi.common.app.ConfigType
import com.luuuzi.common.net.callback.*
import com.luuuzi.common.net.error.ApiException
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
//        stopLoading();

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
                iError?.onError(e.errorCode, "自定义错误")
            } else {
                //其他错误
            }
        }
        /**
         * 通过反射创建异常处理类
         */
        try {
            var netErrotHandler: Any? = App.getConfiguration(ConfigType.NET_ERROR_HADNLE)
            if (netErrotHandler != null) {
                val c: Class<*> = netErrotHandler as Class<*>
                iNetError = c.newInstance() as INetError
                iNetError?.setThrowable(e, iStatusView)
            }
        } catch (e: Exception) {

        }

        if (iFailure != null) {//请求失败
            iFailure?.onFailure(e.message)
        }
        if (iRequest != null) {//请求结束
            iRequest?.onRequestEnd()
        }
    }
}