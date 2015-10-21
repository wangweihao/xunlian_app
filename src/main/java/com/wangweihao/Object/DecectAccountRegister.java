package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class DecectAccountRegister extends AccessDatabase{
    public DecectAccountRegister() {
    }
    public DecectAccountRegister(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase() {
        System.out.println("准备访问数据库...");
        System.out.println("访问数据库");
        System.out.println("查询成功");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
