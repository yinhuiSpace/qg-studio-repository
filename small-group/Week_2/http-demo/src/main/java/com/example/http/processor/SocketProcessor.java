package com.example.http.processor;

import com.example.http.request.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
public class SocketProcessor implements Runnable{

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

    }

    /**
    * 处理请求：包括确定请求方式，依次分类处理，最终要封装一个对象，并且要根据客户端需要返回数据
    * */
    public void processReq(){

        request=new HttpRequest();

        //初步依据http格式，解析并划分请求组成部分，分不同部分处理


        //解析出请求行

    }

    /**
    * 解析请求
    * */
    public void parseReq()throws Exception{
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
        reqLine=reqInLines[0];

        //大括号括起来的肯定是请求体


        //夹在中间的就是请求头了
    }


    /**
    * 解析请求头
    * */
    private void getReqLine() {
        //解析请求行，先以空格分割，按照http格式，一共存在三部分：请求方式，请求路径（get请求会在路径后拼接参数），请求协议
        String[] lineSplit = reqLine.split(" ");
        //第一个是请求方法
        request.setMethod(lineSplit[0]);
        //第二个是请求路径，还可能有get请求的参数
        //参数map
        HashMap<String, Object> paramMap = new HashMap<>();
        if (lineSplit[0].equalsIgnoreCase("GET")) {
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
            }

            System.out.println(paramMap);
        } else {
            request.setUrl(lineSplit[1]);
        }
        //第三个是请求协议
        request.setProtocol(lineSplit[2]);
    }
}