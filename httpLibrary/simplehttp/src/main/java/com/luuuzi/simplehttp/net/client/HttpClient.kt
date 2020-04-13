package com.luuuzi.simplehttp.net.client

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.luuuzi.simplehttp.net.HttpMethod
import com.luuuzi.simplehttp.net.callback.IError
import com.luuuzi.simplehttp.net.callback.IFailure
import com.luuuzi.simplehttp.net.callback.IRequest
import com.luuuzi.simplehttp.net.callback.ISuccess
import com.luuuzi.simplehttp.widget.loader.LoaderStyle
import com.luuuzi.simplehttp.widget.loader.VCyunLoader
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.lang.ref.WeakReference
import java.nio.charset.Charset
import java.util.*

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 16:43
 *    desc   : 请求封装
 */
class HttpClient(
    var url: String,
    var error: IError?,
    var failure: IFailure?,
    var request: IRequest?,
    var params: WeakHashMap<String, Any>?,
    var loaderStyle: LoaderStyle?,
    context: Context?,
    var is_encrypt: Boolean,//是否加密
    var statusView: IStatusView?,
    var lifecycleTransformer: LifecycleTransformer<*>?
) {
    var CONTEXT: WeakReference<Context>? = null

    init {
        if (context != null) {
            CONTEXT = WeakReference(context)
        }
    }

    private var bodyObservable: Observable<ResponseBody>? = null

    companion object {
        private val mediaType =
            MediaType.parse("application/json;charset=UTF-8")

        fun Builder(): HttpClientBuilder {
            return HttpClientBuilder()
        }
    }

    private fun createBodyObservable(method: HttpMethod) {
        val service: HttpService =
            HttpCreator.getRestService()
        when (method) {
            HttpMethod.GET -> bodyObservable = service.get(url, params)
            HttpMethod.POST -> bodyObservable = service.post(url, params)
            HttpMethod.PUT -> bodyObservable = service.put(url, params)
            HttpMethod.PUTJSON -> {
                var strJson = ""
                if (params != null && params?.size!! > 0) {
                    strJson = Gson().toJson(params)
                }
                bodyObservable = service.putJson(
                    url,
                    RequestBody.create(mediaType, strJson)
                )

            }
            HttpMethod.PUTURL -> bodyObservable = service.putUrl(url, params)
            HttpMethod.DELETE -> bodyObservable = service.delete(url, params)
            HttpMethod.POSTJSON -> {

                var strJson = ""
                if (params != null && params?.size!! > 0) {
                    strJson = Gson().toJson(params)
                }
                bodyObservable = service.postJson(url, RequestBody.create(mediaType, strJson))

            }
            HttpMethod.POSTQUERY -> {
                bodyObservable = service.postQuery(url, params)
            }
            else -> bodyObservable = service.post(url, params)
        }
    }

    fun get(): Request {
        createBodyObservable(HttpMethod.GET)
        return Request()
    }

    fun post(): Request {
        createBodyObservable(HttpMethod.POST)
        return Request()
    }

    fun postJson(): Request {
        createBodyObservable(HttpMethod.POSTJSON)
        return Request()
    }

    fun postQuery(): Request {
        createBodyObservable(HttpMethod.POSTQUERY)
        return Request()
    }

    fun delete(): Request {
        createBodyObservable(HttpMethod.DELETE)
        return Request()
    }

    fun put(): Request {
        createBodyObservable(HttpMethod.PUT)
        return Request()
    }

    fun putJson(): Request {
        createBodyObservable(HttpMethod.PUTJSON)
        return Request()
    }

    fun putUrl(): Request {
        createBodyObservable(HttpMethod.PUTURL)
        return Request()
    }

    inner class Request {
        fun <T> request(clazz: Class<T>?, iSuccess: ISuccess<T>) {
            if (clazz == null) {
                getObservable(Any::class.java)
                    ?.subscribe(
                        BaseObserver(
                            CONTEXT?.get(),
                            failure,
                            iSuccess as ISuccess<Any>,
                            request,
                            loaderStyle,
                            statusView,
                            error
                        )
                    )
            } else {
                getObservable(clazz)
                    ?.subscribe(
                        BaseObserver(
                            CONTEXT?.get(),
                            failure,
                            iSuccess,
                            request,
                            loaderStyle,
                            statusView,
                            error
                        )
                    )
            }
        }

        fun <T> request(iSuccess: ISuccess<T>) {
            this.request(null, iSuccess)
        }

        @SuppressLint("CheckResult")
        fun <T> getObservable(clazz: Class<T>): Observable<T>? {
            var observable: Observable<T>?

            if (request != null) {//开始请求回调
                request?.onRequestStart()
            }

            //显示加载进度框
            if (loaderStyle!=null){
                VCyunLoader.showLoading(CONTEXT!!.get(), loaderStyle)
            }
//            bodyObservable?.map(object : Function<ResponseBody, T> {
//                override fun apply(responseBody: ResponseBody): T {
//                    val source = responseBody.source()
//                    source.request(Long.MAX_VALUE)
//                    val buffer = source.buffer()
//                    val str = buffer.clone().readString(Charset.forName("UTF-8"))
//
//                    return Gson().fromJson<T>(str, clazz)
//                }
//            })
            observable = bodyObservable?.map { responseBody ->
                val source = responseBody.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer()
                val str = buffer.clone().readString(Charset.forName("UTF-8"))

                Gson().fromJson<T>(str, clazz)
            }
            if (observable != null) {
                observable = observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()

            }

            if (lifecycleTransformer != null) {

//                observable = observable!!.compose(lifecycleTransformer)
            }

            return observable
        }
    }
}