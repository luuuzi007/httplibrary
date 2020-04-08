package com.luuuzi.common.net.client

import com.luuuzi.common.app.App
import com.luuuzi.common.app.ConfigType
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 17:22
 *    desc   : 创建Retrofit
 */
class HttpCreator {
    companion object {
        fun getRestService(): HttpService {
            return RestRerviceHolder.rest_service

        }
    }
}

class RestRerviceHolder {
    companion object {
        val rest_service: HttpService =
            RetrofitHolder.RETROFIT_CLIENT.create(HttpService::class.java)
    }
}

class RetrofitHolder {
    companion object {
        private var base_url: String = App.getHttpHost()
        var RETROFIT_CLIENT: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpHolder.OK_HTTP_CLIENT)
            .build()
    }
}

class OkHttpHolder {
    companion object {
        //        private val TIME_OUT = 30
        private const val CONNECT_TIMEOUT: Long = 30 //连接超时/秒
        private const val READ_TIMEOUT: Long = 15 // 读取超时/秒
        private const val WRITE_TIMEOUT: Long = 15 // 写入超时/秒
        private var cache = Cache(File(App.getApplicationContext().getCacheDir(), "OkHttpCache"), 10 * 1024 * 1024)//缓存

        private val OK_HTTP_BUILDER = OkHttpClient.Builder()
        private val INTERCEPTORS: ArrayList<Interceptor>? = App.getConfiguration(ConfigType.INTERCEPTORS)//拦截器

        private fun addInterceptor(): OkHttpClient.Builder {//添加拦截器
            if (INTERCEPTORS != null && INTERCEPTORS.size > 0) {
                INTERCEPTORS.forEach {
                    OK_HTTP_BUILDER.addInterceptor(it)
                }
            }
            return OK_HTTP_BUILDER
        }

        val OK_HTTP_CLIENT: OkHttpClient = addInterceptor()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }
}
