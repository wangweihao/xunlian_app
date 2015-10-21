package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class CreateQRCode extends AccessDatabase {
    public CreateQRCode() {
    }
    public CreateQRCode(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("创建二维码");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
