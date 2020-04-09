package com.luuuzi.simplehttp.net.client

import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 17:09
 *    desc   :
 */
interface HttpService {
    @GET
    operator fun get(@Url url: String?, @QueryMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    @FormUrlEncoded
    @POST
    fun post(@Url url: String?, @FieldMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    //    postjson
//    @FormUrlEncoded//不需要表单
    @POST
    fun postQuery(@Url url: String?, @QueryMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    //传json字符串
//    @FormUrlEncoded
    @POST
    fun postJson(@Url url: String?, @Body params: RequestBody?): Observable<ResponseBody>?


    @FormUrlEncoded
    @PUT
    fun put(@Url url: String?, @FieldMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    //    @Multipart  requestbody不能喝multipart一起使用 传json字符串
    @PUT
    fun putJson(@Url url: String?, @Body params: RequestBody?): Observable<ResponseBody>?

    //put 拼接在url后面
    @Headers("Content-Type: application/json")
    @PUT
    fun putUrl(@Url url: String?, @QueryMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    @DELETE
    fun delete(@Url url: String?, @QueryMap params: WeakHashMap<String, Any>?): Observable<ResponseBody>?

    /**
     * 下载文件
     */
    @Streaming
    @GET
    fun downloadFile(@Url url: String?): Observable<ResponseBody>?

    /**
     * 断点下载文件
     * @param start 从哪个位置开始下载
     * @param url 下载链接
     * @return
     */
    @Streaming
    @GET
    fun downloadFile(@Header("RANGE") start: String?, @Url url: String?): Flowable<ResponseBody>?
}