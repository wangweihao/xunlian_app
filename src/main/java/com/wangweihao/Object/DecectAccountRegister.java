package com.wangweihao.Object;

/**
 * Created by wwh on 15-10-21.
 */
public class DecectAccountRegister {
    DecectAccountRegister(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    private RecvBasicMessageObject basicObject;
}
