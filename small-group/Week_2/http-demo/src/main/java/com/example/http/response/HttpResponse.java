package com.example.http.response;

import com.example.http.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author root
 * @packageName: com.example.http.response
 * @className: HttpResponse
 * @description: 将数据封装成响应对象，再由web容器序列化成数据流传给客户端
 * @date 2024/3/27 8:39
 */
public class HttpResponse {

    //请求头常量
    private static final String TYPE="Content-Type";

    private static final String LENGTH="Content-Length";

    private static final String LOCATION="Location";

    private static final String JSON="application/json;charset=utf-8";

    private static final String TEXT="text/plain;charset=utf-8";

    private static final String HTML="application/html;charset=utf-8";

    //将响应信息封装成响应对象
    //响应状态
    private int status;

    //响应信息
    private String msg;

    //状态码常量
    public static final int OK=200;

    public static final int NOT_FOUND=404;

    public static final int NOT_AUTH=403;

    public static final int SERVER_ERR=500;

    public static final int RE_URL=302;

    //响应头
    private Map<String,String> headers=new HashMap<>();

    //客户端输出流

    //符号常量
    private static final byte SPACE=' ';
    //换行符
    private static final byte CR='\r';

    private static final byte LF='\n';

    //客户端请求数据封装成的请求对象
    private HttpRequest request;

    //客户端接受点对象的输出流，可以通过这里向客户端写入数据
    private OutputStream socketOutputStream;
    //提供一个输出流，供调用者写入数据
    private ServletOutputStream servletOutputStream=new ServletOutputStream();

    //客户端接受点
    private Socket socket;

    public HttpResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public OutputStream getSocketOutputStream() {
        return socketOutputStream;
    }

    public void setSocketOutputStream(OutputStream socketOutputStream) {
        this.socketOutputStream = socketOutputStream;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 添加请求头
     * */
    public void addHeader(String s, String s1) {
        headers.put(s,s1);
    }

    /**
    * 获取输出流
    * */


    /**
     * 获取输入流
     * */
    public PrintWriter getWriter() throws IOException {

        return new PrintWriter(getOutputStream());
    }

    /**
     * 设置响应码
     * */


    /**
     * 获取输出流对象，给调用者提供一个输出流用于写入数据
     * */
    public ServletOutputStream getOutputStream() throws IOException {

        return servletOutputStream;
    }


    /**
     * 重定向
     * */
    public void sendRedirect(String location) throws IOException {
        //将状态码设为302
        setStatus(RE_URL);
        //设置响应头
        addHeader(LOCATION,location);

    }

    /**
     * 完成请求响应
     * */
    public void complete()throws Exception{
        //发送响应行
        writeRespLine();

        //发送响应头
        writeRespHeader();

        //发送响应体
        writeRespBody();

        //关闭通道
        socketOutputStream.close();
        socket.close();
    }

    /**
     * 输出响应体
     * */
    private void writeRespBody() throws Exception{
        //从暴露给调用者的输出流处获取字节数据
        socketOutputStream.write(servletOutputStream.getBytes());
    }

    /**
     * 写入响应头
     * */
    private void writeRespHeader() throws IOException{
        //默认添加的请求头
        if (!headers.containsKey(LENGTH)){
            addHeader(LENGTH,String.valueOf(servletOutputStream.getPos()));
        }
        if (!headers.containsKey(TYPE)){
            addHeader(TYPE,JSON);
        }

        //用户指定的请求头
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            //遍历响应头键值对
            String key = entry.getKey();
            String value = entry.getValue();
            //将键值对依次输出
            socketOutputStream.write(key.getBytes());
            socketOutputStream.write(":".getBytes());
            socketOutputStream.write(value.getBytes());
            socketOutputStream.write(CR);
            socketOutputStream.write(LF);
        }
        //响应头和响应体之间分隔一行
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);
    }

    /**
     * 写入响应行
     * */
    private void writeRespLine() throws IOException{

        //调用输出流对象（注意换行与空格）
        //写入协议字节数据
        socketOutputStream.write(request.getProtocol().getBytes());
        socketOutputStream.write(SPACE);
        //写入状态码
        socketOutputStream.write(status);
        socketOutputStream.write(SPACE);
        //写入提示信息
        socketOutputStream.write(msg.getBytes());
        socketOutputStream.write(CR);
        socketOutputStream.write(LF);

    }
}
