package com.fox.example.proxy;

import com.fox.example.annontation.Select;
import com.fox.example.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.isAnnotationPresent(Select.class)){
            Select select = method.getAnnotation(Select.class);
            String sql = select.sql();
            Object o = sqlSession.selectOne(sql, args);
            return o;
        }
        //insert delete update 暂时不写
        return null;
    }

}
