package com.fox.example.session;

import com.fox.example.druid.MyDruid;
import com.fox.example.executer.Execoter;
import com.fox.example.proxy.MapperProxy;

import java.lang.reflect.Proxy;

public class SqlSession {


    private Execoter execoter;

    private MyDruid myDruid;

    public SqlSession(MyDruid myDruid){
        this.myDruid = myDruid;
        this.execoter = new Execoter(myDruid);

    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader()
                ,new Class[]{clazz},new MapperProxy(this));
    }

    public <T> T selectOne(String sql,Object[] args){
        return execoter.selectOne(sql,args);
    }
}
