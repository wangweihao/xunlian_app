package com.wangweihao.Object;

import sun.security.pkcs11.P11Util;

import java.util.HashMap;

/**
 * Created by wwh on 15-10-21.
 */
public class MarkToClassObjectMap {
    /* 第一层括号为匿名内部类，第二层括号为实例初始化块 */
    HashMap<Integer, String> markToClass = new HashMap<Integer, String>(){
        {
            put(1, "DectAccountRegister");
        }
    };
}
