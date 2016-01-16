package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

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
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("修改资料");
        setDerivedClassOtherMeber();
        sqlString = "update UserInfo set name = \'" + Name + "\', head = \'"
                + Head + "\' where account = \'" + basicObject.getAccount() + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        ConstructSelfInfo(preparedStatement.executeUpdate());

        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        Name = new String(jsonObject.getString("name"));
        Head = jsonObject.getInt("head");
    }

    @Override
    public void ConstructSelfInfo(int SuccessOrFailure){
        if (SuccessOrFailure == 1) {
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"修改资料成功\"}}";
        } else {
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"修改资料失败\"}}";
        }
    }

    private String Name;
    private int Head;
}
