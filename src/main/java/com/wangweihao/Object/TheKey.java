package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by wwh on 15-11-25.
 */
public class TheKey extends AccessDatabase {
    public TheKey(){
        super();
    }
    public TheKey(int _mark, String _account) {
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        setDerivedClassOtherMeber();
        addTheKey();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        JSONObject keyJson = new JSONObject(RequestString);
        theKey = new String(keyJson.getString("theKey"));
    }

    private void addTheKey() throws SQLException {
        String addTheKeySql = "update UserInfo set theKey = \"" + theKey + "\" where account = \"" +
                basicObject.getAccount() + "\";";
        JSONObject retInfo = new JSONObject();
        JSONObject Info = new JSONObject();
        retInfo.put("data", ObtainData.getData());
        retInfo.put("account", basicObject.getAccount());
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("mark", basicObject.getMark());
        preparedStatement = DBPoolConnection.prepareStatement(addTheKeySql);
        try{
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            retInfo.put("error", 1);
            retInfo.put("status", "failure");
            Info.put("IsSuccess", "failure");
            Info.put("ResultINFO", "设置Key值失败");
            return;
        }
        retInfo.put("error", 0);
        retInfo.put("status", "success");
        Info.put("IsSuccess", "success");
        Info.put("ResultINFO", "设置Key值成功");
        retInfo.put("result", Info);
        ResponseString = retInfo.toString();
    }

    String theKey;
}
