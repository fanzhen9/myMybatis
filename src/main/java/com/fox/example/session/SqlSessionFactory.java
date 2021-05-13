package com.fox.example.session;

import com.fox.example.druid.MyDruid;

public class SqlSessionFactory {

    public SqlSession build(){
        MyDruid myDruid = new MyDruid(1,5);
        return new SqlSession(myDruid);
    }
}
