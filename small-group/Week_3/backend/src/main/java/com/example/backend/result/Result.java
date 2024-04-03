package com.example.backend.result;

/**
 * @author root
 * @packageName: com.example.backend.result
 * @className: Result
 * @description: 统一返回结果
 * @date 2024/4/3 9:01
 */
public class Result<T> {

    //状态码
    private String code;

    //状态描述信息
    private String msg;

    //响应数据
    private T data;

    public Result() {
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //请求成功并且返回数据
    public static <U> Result<U> success(U data){

        return new Result<>("200","success",data);
    }

    //请求成功，但是没有返回数据
    public static <U> Result<U> success(){

        return success(null);
    }

    //请求异常，返回异常信息
    public static <U> Result<U> error(String code,String msg){

        return new Result<>(code,msg,null);
    }
}
