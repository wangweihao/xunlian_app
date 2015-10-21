package com.wangweihao.Object;

/**
 * Created by wwh on 15-10-21.
 * 服务端接受的对象基类
 */

public class RecvBasicMessageObject {
    int mark;
    String account;
    public RecvBasicMessageObject(){}

    public RecvBasicMessageObject(int _mark, String _account){
        mark = _mark;
        account = _account;
    }

    public int getMark(){
        return mark;
    }

    public String getAccount(){
        return account;
    }

    public void setMark(int _mark){
        mark = _mark;
    }

    public void setAccount(String _account){
        account = _account;
    }

    public void setValue(RecvBasicMessageObject object){
        mark = object.getMark();
        account = object.getAccount();
    }
}
