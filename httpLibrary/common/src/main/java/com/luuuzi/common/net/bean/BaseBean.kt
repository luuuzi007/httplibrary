package com.luuuzi.common.net.bean

/**
 * 与业务相关的数据模型基类
 */
class BaseBean {
    var response_code = 0
    var response_message: String? = null
    var code = 0//正确返回这个
    var message : String =""
    //    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
    var page_count = 0
    var num_count = 0
    var page_size = 0
    var page_index = 0

}