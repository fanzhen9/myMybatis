package com.fox.example;

import com.fox.example.doman.User;
import com.fox.example.druid.MyDruid;
import com.fox.example.mapper.UserMapper;
import com.fox.example.session.SqlSession;
import com.fox.example.session.SqlSessionFactory;

import java.sql.Connection;

public class App {

    public static void main(String[] args) {
        /*MyDruid myDruid = new MyDruid(1,5);
        System.out.println(myDruid);
        Connection collection = myDruid.getCollection();
        myDruid.release(collection);*/
        SqlSession sqlSession = new SqlSessionFactory().build();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById("2");
        System.out.println(user.toString());
    }
}
