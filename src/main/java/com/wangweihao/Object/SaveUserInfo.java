package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-27.
 */
public class SaveUserInfo extends AccessDatabase{
    public SaveUserInfo(){
        super();
        UserInfo = new ResultObject();
    }
    public SaveUserInfo(int _mark, String _account) {
        super(_mark, _account);
        UserInfo = new ResultObject();
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("接受用户信息");
        setDerivedClassOtherMeber();
        saveUserInfo();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        JSONObject personInfoJson= jsonObject.getJSONObject("personInfo");
        JSONObject emailInfoJson = jsonObject.getJSONObject("emailInfo");
        JSONObject phoneInfoJson = jsonObject.getJSONObject("phoneInfo");
        UserInfo.setName(personInfoJson.getString("name"));
        UserInfo.setHead(personInfoJson.getString("head"));
        UserInfo.setPersonNumber(phoneInfoJson.getString("personPhoneNumber"));
        UserInfo.setHomePhoneNumber(phoneInfoJson.getString("homePhoneNumber"));
        UserInfo.setWorkPhoneNumber(phoneInfoJson.getString("workPhoneNumber"));
        UserInfo.setPersonEmail(emailInfoJson.getString("personMailNumber"));
        UserInfo.setHomeEmail(emailInfoJson.getString("homeMailNumber"));
        UserInfo.setWorkEmail(emailInfoJson.getString("workMailNumber"));
        UserInfo.setQqNumber(jsonObject.getString("qqNumber"));
        UserInfo.setWeiboNumber(jsonObject.getString("weiboNumber"));
    }

    private void saveUserInfo() throws SQLException {
        saveNameAndHead();
        saveUserContact();
    }

    private void saveUserContact() throws SQLException {
        JSONObject retInfo = new JSONObject();
        JSONObject result = new JSONObject();
        retInfo.put("status", "success");
        retInfo.put("data", ObtainData.getData());
        result.put("requestPhoneNum", basicObject.getAccount());
        try {
            SaveContact(0x1, UserInfo.getPersonNumber());
            SaveContact(0x2, UserInfo.getHomePhoneNumber());
            SaveContact(0x4, UserInfo.getWorkPhoneNumber());
            SaveContact(0x8, UserInfo.getPersonEmail());
            SaveContact(0x10, UserInfo.getHomeEmail());
            SaveContact(0x20, UserInfo.getWorkEmail());
            SaveContact(0x40, UserInfo.getQqNumber());
            SaveContact(0x80, UserInfo.getWeiboNumber());
            retInfo.put("error", 0);
            result.put("ResultINFO", "保存成功");
            result.put("IsSuccess", "success");
            retInfo.put("result", result);
        }catch (SQLException e){
            e.printStackTrace();
            retInfo.put("error", 1);
            result.put("ResultINFO", "保存失败");
            result.put("IsSuccess", "failure");
            retInfo.put("result", result);
        }
        ResponseString = retInfo.toString();
    }

    private void getUserId() throws SQLException {
        String sqlGetUserId =  "select uid from UserInfo where account = \"" + basicObject.getAccount() + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        UserId = resultSet.getInt(1);
    }

    private void SaveContact(int type, String content) throws SQLException {
        /* 如果发来的内容为空串，说明用户没有填写信息。 插入空数据，后面直接更新即可 */
        String sqlSaveContact = "insert into UserContact (uid, type, content) values (" + UserId +
                ", " + type + ", \"" + content + "\");";
        System.out.println(sqlSaveContact);
        preparedStatement = DBPoolConnection.prepareStatement(sqlSaveContact);
        preparedStatement.executeUpdate();
        System.out.println("向数据库中插入数据...................");
    }

    private void saveNameAndHead() throws SQLException {
        getUserId();
        String sqlSaveNameAndHead = "update UserInfo set name = \"" + UserInfo.getName() + "\", head = \"" +
                UserInfo.getHead() + "\" where account = \"" + basicObject.getAccount() + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlSaveNameAndHead);
        preparedStatement.executeUpdate();
    }

    private ResultObject UserInfo;
    private int UserId;
    private ResultSet resultSet;
}
