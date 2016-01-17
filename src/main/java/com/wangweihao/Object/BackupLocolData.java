package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

import java.sql.SQLException;

/**
 * Created by wwh on 16-1-16.
 */
public class BackupLocolData extends AccessDatabase {
    public BackupLocolData(){
        super();
    }
    public BackupLocolData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException{
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){

    }
}
