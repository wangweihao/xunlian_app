package com.wangweihao.Object;

/**
 * Created by wwh on 15-10-21.
 * 服务端接受的对象基类
 */

public class ServerRecvMessageObject {
    int mark;
    String account;
    ServerRecvMessageObject(int _mark, String _account){
        mark = _mark;
        account = _account;
    }
    int getMark(){
        return mark;
    }
    String getAccount(){
        return account;
    }
}
