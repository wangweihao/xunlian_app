package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ModifySecret extends AccessDatabase{
    public ModifySecret(){
    }

    public ModifySecret(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase(){
        System.out.println("修改密码");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}