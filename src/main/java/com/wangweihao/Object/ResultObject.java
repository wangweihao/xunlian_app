package com.wangweihao.Object;

/**
 * Created by wwh on 15-10-27.
 */
public class ResultObject {
    private String name;
    private String head;
    private String personNumber;
    private String workPhoneNumber;
    private String homePhoneNumber;
    private String personEmail;
    private String workEmail;
    private String homeEmail;
    private String qqNumber;
    private String weiboNumber;
    int error;
    private String status;

    public void setError(){
        error = 1;
        status = new String("failure");
    }

    public String getName() {
        return name;
    }

    public String getHead() {
        return head;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public String getWeiboNumber() {
        return weiboNumber;
    }

    public int getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public void setWeiboNumber(String weiboNumber) {
        this.weiboNumber = weiboNumber;
    }

    public void setError(int error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
