package com.wangweihao.ContactType;

/**
 * Created by wwh on 15-10-23.
 */
public class Contact {
    /* 电话，邮箱，QQ等联系方式类型 */
    private int ContactType;
    /* 内容 */
    private String Contact;

    public Contact(int _contactType, String _contact){
        ContactType = _contactType;
        Contact = _contact;
    }

    public int getContactType() {
        return ContactType;
    }

    public String getContact(){
        return Contact;
    }

    public void setContactType(int contactType) {
        ContactType = contactType;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void update(int type, String contact){
        ContactType = type;
        Contact = contact;
    }
}
