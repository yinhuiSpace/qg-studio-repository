package com.example.http.response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author root
 * @packageName: com.example.http.response
 * @className: ServletOutputStream
 * @description:
 * @date 2024/3/27 8:43
 */
public class ServletOutputStream extends OutputStream {

    //缓存字节数组
    private byte[] bytes=new byte[8192];

    //缓存数组索引，充当指针作用
    private int pos=0;

    public byte[] getBytes() {
        return bytes;
    }

    public int getPos() {
        return pos;
    }

    /**
     * 实质是实现outputStream的write方法
     * */
    public void write(int b) throws IOException {
        //逐个字节写入，ascii字码占一个字节
        System.out.println(b);
        //将单字节缓存为字节数组
        bytes[pos]=(byte) b;
        pos++;
    }

}
