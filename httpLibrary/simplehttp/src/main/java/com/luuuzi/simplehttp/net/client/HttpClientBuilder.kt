package com.luuuzi.simplehttp.net.client

import android.content.Context
import com.luuuzi.simplehttp.net.callback.IError
import com.luuuzi.simplehttp.net.callback.IFailure
import com.luuuzi.simplehttp.net.callback.IRequest
import com.luuuzi.simplehttp.widget.loader.LoaderStyle
import com.trello.rxlifecycle2.LifecycleTransformer
import java.util.*

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 16:50
 *    desc   : 建造者模式创建httpclient
 */
class HttpClientBuilder {
    private var PARAMS: WeakHashMap<String, Any>? = null
    private var mUrl: String? = null
    private var mIError: IError? = null
    private var mIFailure: IFailure? = null
    private var mIRequest: IRequest? = null
    private var mContext: Context? = null
    private var mIsEncrypt = true
    private var mLoaderStyle:LoaderStyle?=null
    private var mStatusView: IStatusView? = null
    private var lifecycleTransformer: LifecycleTransformer<*>? = null
    private var strJson: String? = null

    init {
        PARAMS = WeakHashMap()
    }


    fun setLifecycleTransformer(lifecycleTransformer: LifecycleTransformer<*>?): HttpClientBuilder {
        this.lifecycleTransformer = lifecycleTransformer
        return this
    }

    fun url(url: String?): HttpClientBuilder {
        mUrl = url
        return this
    }

    fun params(params: WeakHashMap<String, Any>?): HttpClientBuilder {
        PARAMS!!.putAll(params!!)
        return this
    }

    fun params(key: String, value: Any): HttpClientBuilder {
        PARAMS!![key] = value
        return this
    }


    fun request(mIRequest: IRequest?): HttpClientBuilder {
        this.mIRequest = mIRequest
        return this
    }

    fun error(mIError: IError?): HttpClientBuilder {
        this.mIError = mIError
        return this
    }

    fun failure(mIFailure: IFailure?): HttpClientBuilder {
        this.mIFailure = mIFailure
        return this
    }

    fun bindStatusView(mStatusView: IStatusView?): HttpClientBuilder {
        this.mStatusView = mStatusView
        return this
    }

    /**
     * @param mContext
     * @param mLoaderStyle dialog样式
     */
    fun loader(mContext: Context?,mLoaderStyle: LoaderStyle?): HttpClientBuilder {
        this.mLoaderStyle = mLoaderStyle
        this.mContext = mContext
        return this
    }

    fun loader(mContext: Context?): HttpClientBuilder {
        this.mLoaderStyle = LoaderStyle.LineSpinFadeLoaderIndicator
        this.mContext = mContext
        return this
    }


    fun isEncrypt(isEncrypt: Boolean): HttpClientBuilder {
        mIsEncrypt = isEncrypt
        return this
    }

    fun strJson(strJson: String?): HttpClientBuilder {
        this.strJson = strJson
        return this
    }

    fun build(): HttpClient {
        return HttpClient(
            mUrl!!, mIError, mIFailure, mIRequest,
            PARAMS, mLoaderStyle,mContext, mIsEncrypt, mStatusView, lifecycleTransformer
        )
    }
}