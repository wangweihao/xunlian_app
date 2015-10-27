package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-27.
 */
public class SaveUserInfo extends AccessDatabase{
    SaveUserInfo(){ super(); }
    SaveUserInfo(int _mark, String _account){
        super(_mark, _account);
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
        UserInfo.setName(jsonObject.getString("name"));
        UserInfo.setHead(jsonObject.getString("head"));
        UserInfo.setPersonNumber(jsonObject.getString("personPhoneNumber"));
        UserInfo.setHomePhoneNumber(jsonObject.getString("homePhoneNumber"));
        UserInfo.setWorkPhoneNumber(jsonObject.getString("workPhoneNumber"));
        UserInfo.setPersonEmail(jsonObject.getString("personMailNumber"));
        UserInfo.setHomeEmail(jsonObject.getString("homeMailNumber"));
        UserInfo.setWorkPhoneNumber(jsonObject.getString("workMailNumber"));
        UserInfo.setQqNumber(jsonObject.getString("qqNumber"));
        UserInfo.setWeiboNumber(jsonObject.getString("weiboNumber"));
    }

    private void saveUserInfo() throws SQLException {
        saveNameAndHead();
        saveUserContact();
    }

    private void saveUserContact() throws SQLException {
        SaveContact(0x1, UserInfo.getPersonNumber());
        SaveContact(0x2, UserInfo.getWorkPhoneNumber());
        SaveContact(0x4, UserInfo.getHomePhoneNumber());
        SaveContact(0x8, UserInfo.getPersonEmail());
        SaveContact(0x10, UserInfo.getWorkEmail());
        SaveContact(0x20, UserInfo.getHomeEmail());
        SaveContact(0x40, UserInfo.getQqNumber());
        SaveContact(0x80, UserInfo.getWeiboNumber());
        JSONObject retInfo = new JSONObject();
        JSONObject result = new JSONObject();
        retInfo.put("data", ObtainData.getData());
        retInfo.put("error", 0);
        retInfo.put("statuc", "success");
        result.put("requestPhoneNum", ObtainData.getData());
        result.put("ResultINFO", "保存成功");
        retInfo.put("result", result);
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
        String sqlSaveContact = "insert into UserContact (uid, type, content) values (" + UserId +
                ", " + type + ", \"" + content + "\");";
        preparedStatement.executeUpdate();
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
