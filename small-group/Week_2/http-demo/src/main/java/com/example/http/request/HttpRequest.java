package com.example.http.request;

import java.net.Socket;
import java.util.Map;

/**
 * @author root
 * @packageName: com.example.http.request
 * @className: HttpRequest
 * @description: 将数据流解析得到的，符合tcp协议的数据封装成一个实体对象
 * @date 2024/3/22 20:00
 */
public class HttpRequest {

    //将请求数据封装成对象
    //请求方法
    private String method;

    //请求路径
    private String url;

    //请求协议
    private String protocol;

    //请求参数
    private Map<String,Object> params;

    //位于请求体中的参数
    private Map<String,Object> data;

    //接收点
    private Socket socket;

    public HttpRequest() {
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
