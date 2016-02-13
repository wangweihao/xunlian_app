package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 16-2-14.
 */
public class CheckUpdateIsSuccess extends AccessDatabase{
    public CheckUpdateIsSuccess(){
        super();
    }
    public CheckUpdateIsSuccess(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        if(getUserUid() && delteUpdateFlag()){
            System.out.println("下拉刷新操作成功");
        }else {
            System.out.println("下拉刷新操作失败");
        }
        return this;
    }

    private boolean getUserUid() throws SQLException{
        String getUserUidSql = "select uid from UserInfo where account = \"" +
                basicObject.getAccount() + "\"";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getUserUidSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userUid = resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "好像出了点问题，稍后再试哦~亲~");
            return false;
        }
        return true;
    }

    public boolean delteUpdateFlag() throws SQLException {
        String deleteUpdateFlagSql = "update UserFriend set isUpdate = 0 where uid = \"" +
                userUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(deleteUpdateFlagSql);
            preparedStatement.executeUpdate();
            buildReturnValue(0, "success", "success", "下拉刷新成功~~,快看看谁更新联系方式啦");
        }catch (SQLException e){
            buildReturnValue(1, "success", "failure", "系统错误,请稍后再试哦~");
            return false;
        }
        return true;
    }

    private ResultSet resultSet;
    private int userUid;
}
