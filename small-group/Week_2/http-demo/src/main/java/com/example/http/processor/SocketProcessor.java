package com.example.http.processor;

import com.alibaba.fastjson2.JSONObject;
import com.example.http.request.HttpRequest;
import com.example.http.response.HttpResponse;
import com.example.http.result.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author root
 * @packageName: com.example.http.processor
 * @className: SocketProcessor
 * @description: 专门用于解析客户端传来的数据流，找出并封装有用的信息
 * @date 2024/3/22 19:54
 */
public class SocketProcessor implements Runnable {

    //
    private Socket socket;

    //请求行
    private String reqLine;

    //请求头
    private String reqHeader;

    //请求体
    private String reqBody;

    private HttpRequest request;


    public SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        processReq();
    }

    /**
     * 处理请求：包括确定请求方式，依次分类处理，最终要封装一个对象，并且要根据客户端需要返回数据
     */
    public void processReq() {

        try {
            request = new HttpRequest();

            //初步依据http格式，解析并划分请求组成部分，分不同部分处理
            parseReq();

            //解析出请求行
            getReqLine();

            //解析请求头
            getHeader();

            //封装响应对象并返回给客户端
            sendResp();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 解析请求
     */
    public void parseReq() throws Exception {
        //接收请求数据
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);

        //读取字符流
        //设置中转站
        char[] req = new char[8196];
        reader.read(req);
        //处理字符数组
        String reqStr = new String(req);
        //以换行符分割
        String[] reqInLines = reqStr.split("[\r\n]");
        //现在就从数据流中解析出一行一行的数据了
        //第一行肯定是请求行
        reqLine = reqInLines[0];

        //大括号括起来的肯定是请求体
        if (reqStr.contains("{")) {
            String reqBodyStr = reqStr.split("\\{")[1];

            reqBody = "{" + reqBodyStr;
        }

        //夹在中间的就是请求头了

        if (reqStr.contains("{")) {
            reqHeader = reqStr.substring(reqStr.indexOf("\r\n") + 1, reqStr.indexOf("{"));

            //解析请求体
            getReqBody();
        }
    }

    private void getHeader() {
        //提取出请求头中的键值对
        String[] paramEntry = reqHeader.split("[\r\n]");

        HashMap<String, Object> headerMap = new HashMap<>();

        for (String entry : paramEntry) {
            if (!entry.contains(":")) {
                continue;
            }
            //以冒号分割
            String[] entryStr = entry.split(":");

            String key = entryStr[0];
            String val = entryStr[1];

            headerMap.put(key, val);
        }

        //封装进请求对象中
        request.setParams(headerMap);
    }


    /**
     * 解析请求行
     */
    private void getReqLine() {
        //解析请求行，先以空格分割，按照http格式，一共存在三部分：请求方式，请求路径（get请求会在路径后拼接参数），请求协议
        String[] lineSplit = reqLine.split(" ");
        //第一个是请求方法
        request.setMethod(lineSplit[0]);
        //第二个是请求路径，还可能有get请求的参数
        //参数map
        HashMap<String, Object> paramMap = new HashMap<>();
        if (lineSplit[0].equalsIgnoreCase("GET") && lineSplit[1].contains("?")) {
            //是get请求，需要解析出参数
            String getUrl = lineSplit[1];
            String[] split = getUrl.split("\\?");
            request.setUrl(split[0]);
            //当get请求携带参数才解析参数
            if (split.length > 1) {
                //取出参数列表
                String params = split[1];
                String[] param = params.split("&");
                for (String entry : param) {
                    String[] entryArr = entry.split("=");
                    paramMap.put(entryArr[0], entryArr[1]);
                }

                //将请求行携带的参数封装进请求对象中
                request.setParams(paramMap);
            }
        } else {
            request.setUrl(lineSplit[1]);
        }
        //第三个是请求协议
        request.setProtocol(lineSplit[2]);
    }

    /**
     * 解析请求头
     * */


    /**
     * 解析请求体
     */
    private void getReqBody() {
        //提取请求体部分的json字符串，以大括号分割
        JSONObject jsonObject = JSONObject.parseObject(reqBody);

        HashMap<String, Object> bodyMap = new HashMap<>(jsonObject);

        request.setData(bodyMap);
    }

    /**
     * 发送响应
     */
    public void sendResp() throws Exception {

        //封装响应对象
        HttpResponse response = new HttpResponse();

        PrintWriter out = response.getWriter();

        out.println(JSONObject.toJSONString(Result.success(request)));
    }


}
