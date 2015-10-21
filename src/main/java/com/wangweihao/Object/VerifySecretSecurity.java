package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class VerifySecretSecurity extends AccessDatabase{
    public VerifySecretSecurity(){
        basicObject = new RecvBasicMessageObject();
    }

    public VerifySecretSecurity(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase(){
        System.out.println("验证密宝");
        return basicObject;
    }

}
