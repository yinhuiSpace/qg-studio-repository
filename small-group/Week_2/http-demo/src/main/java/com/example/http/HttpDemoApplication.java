package com.example.http;

import com.example.http.processor.SocketProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author root
 * @packageName: com.example.http
 * @className: HttpDemoApplication
 * @description:
 * @date 2024/3/22 19:46
 */
public class HttpDemoApplication {
    //客户端和服务端通讯，基于TCP协议
    //要处理的请求方式：get,post,put,delete,head,connect,options,trace
    //状态码：1，2，3，4，5

    /**
    * 服务端阻塞等待客户端请求到达
    * */
    private void waitAndGetReq() throws IOException {
        //监听固定端口，该端口是服务端，客户端通讯的通道，数据流在此往返
        ServerSocket server = new ServerSocket(13900);
        while (true) {
            //阻塞并且接收请求，必须要重复执行
            Socket socket = server.accept();
            //用另外一个线程处理请求数据，接收到一个请求，就新开一个线程处理
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            threadPool.execute(new SocketProcessor(socket));
        }
    }
}
