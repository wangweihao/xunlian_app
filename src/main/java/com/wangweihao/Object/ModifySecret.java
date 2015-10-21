package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ModifySecret extends AccessDatabase{
    public ModifySecret(){
        super();
    }

    public ModifySecret(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase(){
        System.out.println("修改密码");
        return basicObject;
    }


}