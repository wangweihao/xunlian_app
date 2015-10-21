package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class VerifySecertSecurity extends AccessDatabase{
    public VerifySecertSecurity(){
    }

    public VerifySecertSecurity(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase(){
        System.out.println("验证密宝");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
