package com.luuuzi.simplehttp.net

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 16:44
 *    desc   :
 */
class HttpResultCode {
    companion object {
        /**
         * 服务器异常
         */
        val SERVICE_ERROR = -1
        /**
         * 请求成功
         */
        val REQUEST_SUCCESS = 200
        /**
         * 未绑定返回
         */
        val THREE_SUCCESS = 1407
        /**
         * token过期
         */
        val TOKEN_PAST_DUE = 401
        /**
         * 验证码错误
         */
        val VERIFICATION_CODE_ERROR = 300
        /**
         * 无数据
         */
        val NO_DATA = 210

        /**
         * 没有文件
         */
        val NO_FILE = 11
        /**
         * 没有任务
         */
        val NO_TASK = 12
        /**
         * 没有消息
         */
        val NO_MSG = 13
        /**
         * 加密信息错误
         */
        val ENCRYPTION_RRROR = 1000
        val NUMBER_OF_FINISHED = 1219 //已到达最大考试次数

        /**
         * 数据验证错误
         */
        val DATA_VALIDATION_ERROR = 1001
        /**
         * 请登录
         */
        val PLEASE_LOGIN = 1003
        /**
         * 没有该接口
         */
        val NO_API = 1004
        /**
         * 请求频繁
         */
        val FREQUENT_REQUEST = 1005
        /**
         * 需要用户认证（token过期）
         */
        val TOKEN_BE_OVERDUE = 1101
        /**
         * 认证信息错误(token刷新失败)
         */
        val TOKEN_REFRESH_FAIL = 1103
        /**
         * 用户被冻结
         */
        val USERS_ARE_FROZEN = 1104
        /**
         * 企业套餐已过期，请续费
         */
        val BUSINESS_PACKAGE_HAS_EXPIRED = 1105
        /**
         * 文件夹已存在
         */
        val FOLDER_ALREADY_EXISTS = 1106
        /**
         * 原密码错误
         */
        val ERROR_IN_ORIGINAL_PASSWORD = 1107
        /**
         * IO错误
         */
        val IO_ERROR = 1108
        /**
         * 权限不足
         */
        val INSUFFICIENT_AUTHORITY = 1109
        /**
         * Josn语法错误
         */
        val JOSN_SYNTAX_ERROR = 1110
        /**
         * 文件已存在
         */
        val FILE_ALREADY_EXIST = 1111
        /**
         * 该课程类型不属于视频，
         */
        val THIS_COURSE_TYPE_DOES_NOT_BELONG_TO_VIDEOS = 1200
        /**
         * 参数有误
         */
        val INCORRECT_PARAMETERS = 1201
        /**
         * 没有获取保存后的数据
         */
        val NO_SAVED_DATA_WAS_RETRIEVED = 1202
        /**
         * 该机构不存在
         */
        val ORGANIZATION_NO_EXIST = 2000
        /**
         * 没有权限访问
         */
        val NO_PERMISSION = 2001
        /**
         * 机构服务到期
         */
        val ORGANIZATION_SERVICE_HEXPIRED = 2002
        /**
         * 机构功能限制
         */
        val ORGANIZATION_FUNCTION_LIMIT = 2003

        /**
         * 机构空间超出，上传文件失败
         */
        val ORGANIZATION_NO_STORAGE_SPACE = 2010

    }

    private var response_status_code = 0

    fun getResponse_status_code(): Int {
        return response_status_code
    }

    fun setResponse_status_code(response_status_code: Int) {
        this.response_status_code = response_status_code
    }
}