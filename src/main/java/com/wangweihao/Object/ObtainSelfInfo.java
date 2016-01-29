package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import com.wangweihao.HelpClass.OneFriendInfo;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by wwh on 16-1-18.
 */
public class ObtainSelfInfo extends AccessDatabase {
    public ObtainSelfInfo(){
        super();
    }
    public ObtainSelfInfo(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        if(ObtainNameAndHead() && ObtainUserUid() && ObtainSelfContact() && buildReturnInfo()){
            System.out.println("查询成功");
        }else{
            System.out.println("查询失败");
        }
        return this;
    }

    public boolean ObtainNameAndHead() throws SQLException {
        self = new OneFriendInfo();
        String obtainNameAndHeadSql = "select name, head, question, answer, addquestion, addanswer from UserInfo where" +
                " account = \"" + basicObject.getAccount() + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(obtainNameAndHeadSql);
            resultSet = preparedStatement.executeQuery();
            resultBool = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        if(resultBool == false){
            buildReturnValue(1, "success", "failure", "查询有误");
            return false;
        }else {
            self.setName(resultSet.getString(1));
            self.setHead(resultSet.getInt(2));
            self.setQuestion(resultSet.getString(3));
            self.setAnswer(resultSet.getString(4));
            self.setAddQuestion(resultSet.getString(5));
            self.setAddAnswer(resultSet.getString(6));
        }
        return true;
    }

    public boolean ObtainSelfContact() throws SQLException {
        contactJson = new JSONObject();
        contactJson.put("account", basicObject.getAccount());
        contactJson.put("personNumber", "");
        contactJson.put("workNumber", "");
        contactJson.put("homeNumber", "");
        contactJson.put("personEmail", "");
        contactJson.put("workEmail", "");
        contactJson.put("homeEmail", "");
        contactJson.put("qqNumber", "");
        contactJson.put("weiboNumber", "");
        contactJson.put("name", self.getName());
        contactJson.put("head", self.getHead());
        contactJson.put("question", self.getQuestion());
        contactJson.put("answer", self.getAnswer());
        contactJson.put("addquestion", self.getAddQuestion());
        contactJson.put("addanswer", self.getAddAnswer());
        String obtainSelfContactSql = "select type, content from UserContact where uid = " + selfUid + ";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(obtainSelfContactSql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        while(resultSet.next()){
            contactJson.put(ContactType.ContactMap.get(resultSet.getInt(1)), resultSet.getString(2));
        }
        return true;
    }

    public boolean ObtainUserUid() throws SQLException {
        boolean bool;
        String obtainUserUidSql = "select uid from UserInfo where account = \"" + basicObject.getAccount() +
                "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(obtainUserUidSql);
            resultSet = preparedStatement.executeQuery();
            bool = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        if(bool == true) {
            selfUid = resultSet.getInt(1);
        }else{
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    public boolean buildReturnInfo(){
        JSONObject retJson = new JSONObject();
        JSONObject Info = new JSONObject();

        retJson.put("error", 0);
        retJson.put("status", "success");
        retJson.put("date", ObtainData.getData());
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("IsSuccess", "success");
        Info.put("mark", basicObject.getMark());
        Info.put("ResultINFO", contactJson);
        retJson.put("result", Info);

        ResponseString = retJson.toString();
        return true;
    }

    private ResultSet resultSet;
    private boolean resultBool;
    private OneFriendInfo self;
    private int selfUid;
    JSONObject contactJson;
}
