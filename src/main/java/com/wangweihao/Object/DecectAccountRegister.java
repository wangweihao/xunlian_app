package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class DecectAccountRegister extends AccessDatabase{
    public DecectAccountRegister() {
        System.out.println("asdasd");
    }
    public DecectAccountRegister(int _mark, String _account){
        System.out.println("_++++");
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("检测帐号是否存在");
        return basicObject;
    }

}