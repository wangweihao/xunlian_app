package com.wangweihao.ContactType;

/**
 * Created by wwh on 15-10-23.
 */
public abstract class Contact {
    /* 电话，邮箱，QQ等联系方式类型 */
    private int ContactWayType;
    /* 电话-家庭 电话-工作 具体的联系方式 */
    private int SpecificContactWayType;
    /* 内容 */
    private String Contact;

    public int getContactWayType(){
        return ContactWayType;
    }

    public int getSpecificContactWayType(){
        return SpecificContactWayType;
    }

    public String getContact(){
        return Contact;
    }
}
