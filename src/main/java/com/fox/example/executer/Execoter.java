package com.fox.example.executer;

import com.fox.example.doman.User;
import com.fox.example.druid.MyDruid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Execoter {

    public MyDruid myDruid;

    public Execoter(MyDruid myDruid){
        this.myDruid = myDruid;
    }

    public <T> T selectOne(String sql, Object[] args) {
        Connection collection = myDruid.getCollection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = collection.createStatement();
            for (int i = 0; i < args.length; i++) {
                sql = sql.replace("?","'"+args[i]+"'");
            }
            rs = statement.executeQuery(sql);
            User user = new User();
            while (rs.next()) {
                String id = rs.getString("id");
                String userCode = rs.getString("user_code");
                user.setId(id);
                user.setUserCode(userCode);
            }
            return (T) user;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            myDruid.release(collection);
        }
    }
}
