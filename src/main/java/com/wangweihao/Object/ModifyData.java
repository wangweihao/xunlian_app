package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ModifyData extends AccessDatabase {
    public ModifyData(){
        super();
    }

    public ModifyData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase(){
        System.out.println("修改资料");
        return this;
    }


}
