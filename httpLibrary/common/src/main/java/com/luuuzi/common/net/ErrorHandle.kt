package com.luuuzi.common.net

import android.util.Log
import com.google.gson.JsonSyntaxException
import com.luuuzi.common.net.callback.INetError
import com.luuuzi.common.net.client.IStatusView
import com.luuuzi.common.net.error.ApiException
import com.luuuzi.common.util.log.MLog
import com.luuuzi.common.util.net.NetworkUtils
import com.luuuzi.common.util.toast.ToastUtil
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 17:36
 *    desc   :
 */
class ErrorHandle : INetError {
    override fun setThrowable(e: Throwable, statusView: IStatusView?) {
        MLog.json(MLog.E, "出现异常:" + e.message)
        Log.i("ErrorHandle", "出现异常:" + e.message)


        if (!NetworkUtils.getInstance().isConnected) {
            statusView?.openNoNetView()
            ToastUtil.showToast("没有网络")
        } else {
            if (e is TimeoutException) {
                ToastUtil.showToast("网络请求超时")
                statusView?.openNetErrorView()
            } else if (e is JsonSyntaxException) {
                ToastUtil.showToast("数据解析异常")
            } else if (e is UnknownHostException) {
                ToastUtil.showToast("域名解析异常")
            } else if (e is HttpException) { //HTTP错误
                ToastUtil.showToast("请求异常")
            } else if (e is ApiException) {
                val exception: ApiException = e
                MLog.e("错误码：" + exception.errorCode)
                when (exception.errorCode) {
                    HttpResultCode.SERVICE_ERROR ->  //服务器异常
                        ToastUtil.showToast("服务器异常")
                    HttpResultCode.VERIFICATION_CODE_ERROR -> ToastUtil.showToast("验证码错误")
                    HttpResultCode.NO_DATA ->  //暂无数据
                        statusView?.openDataEmptyView()
                    HttpResultCode.NO_FILE -> ToastUtil.showToast("没有文件")
                    HttpResultCode.NO_TASK -> ToastUtil.showToast("没有任务")
                    HttpResultCode.NO_MSG -> ToastUtil.showToast("没有信息")
                    HttpResultCode.ENCRYPTION_RRROR -> ToastUtil.showToast("加密信息错误")
                    HttpResultCode.DATA_VALIDATION_ERROR -> ToastUtil.showLongToast(exception.errorMessage)
                    HttpResultCode.PLEASE_LOGIN ->  //没有该接口
                        ToastUtil.showToast("请重新登录")
                    HttpResultCode.NO_API ->  //没有该接口
                        ToastUtil.showToast("服务器异常")
                    HttpResultCode.FREQUENT_REQUEST ->  //请求频繁
                        ToastUtil.showToast("请求过于频繁")
                    HttpResultCode.TOKEN_BE_OVERDUE -> {
                    }
                    HttpResultCode.TOKEN_REFRESH_FAIL ->  //                    //认证信息错误(token刷新失败)
//                    //刷新失败，跳转登录界面
                        ToastUtil.showLongToast(exception.errorMessage)
                    HttpResultCode.USERS_ARE_FROZEN -> ToastUtil.showToast("用户被冻结")
                    HttpResultCode.BUSINESS_PACKAGE_HAS_EXPIRED -> ToastUtil.showToast("企业套餐已过期，请续费")
                    HttpResultCode.FOLDER_ALREADY_EXISTS -> ToastUtil.showToast("文件夹已存在")
                    HttpResultCode.ERROR_IN_ORIGINAL_PASSWORD -> ToastUtil.showToast("原密码错误")
                    HttpResultCode.IO_ERROR -> ToastUtil.showToast("IO错误")
                    HttpResultCode.INSUFFICIENT_AUTHORITY -> ToastUtil.showToast("权限不足")
                    HttpResultCode.JOSN_SYNTAX_ERROR -> ToastUtil.showToast("Josn语法错误")
                    HttpResultCode.FILE_ALREADY_EXIST -> ToastUtil.showToast("文件已存在")
                    HttpResultCode.THIS_COURSE_TYPE_DOES_NOT_BELONG_TO_VIDEOS -> ToastUtil.showToast(
                        "该课程类型不属于视频，文档，考试"
                    )
                    HttpResultCode.INCORRECT_PARAMETERS -> ToastUtil.showToast("参数有误")
                    HttpResultCode.NO_SAVED_DATA_WAS_RETRIEVED -> ToastUtil.showToast("没有获取保存后的数据")
                    HttpResultCode.ORGANIZATION_NO_EXIST -> ToastUtil.showToast("该机构不存在")
                    HttpResultCode.NO_PERMISSION -> ToastUtil.showToast("没有权限访问")
                    HttpResultCode.ORGANIZATION_SERVICE_HEXPIRED -> ToastUtil.showToast("机构服务到期")
                    HttpResultCode.ORGANIZATION_FUNCTION_LIMIT -> ToastUtil.showToast("机构功能限制")
                    HttpResultCode.ORGANIZATION_NO_STORAGE_SPACE -> ToastUtil.showToast("机构空间超出，上传文件失败")
                    else -> {
                        Log.i("ErrorHandle","其他错误：-------》$statusView")

                        statusView?.openDataEmptyView()
                    }
                }
            } else {
                Log.i("ErrorHandle", "请求超时错误：" + e.message)
                if (e.message == "Failed to connect to /192.168.1.6:8083" || e.message == "Failed to connect to /api.rhinostar.com") { //服务器异常
                    statusView?.serverMaintain()
                } else {
                    ToastUtil.showToast("请求超时")
                    statusView?.openNetErrorView()
                }
            }
        }
    }
}