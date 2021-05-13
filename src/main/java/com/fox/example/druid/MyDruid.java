package com.fox.example.druid;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyDruid {

    private Queue<Connection> connections;

    private Properties properties = new Properties();

    private Lock lock = new ReentrantLock();

    private Integer count = 0;

    private int coreSize;

    private int maxSize;


    public MyDruid(Integer coreSize,Integer maxSize){
        try {
            lock.lock();
            this.coreSize = coreSize;
            this.maxSize = maxSize;
            properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
            connections = new ArrayBlockingQueue<>(maxSize);
            for (Integer i = 0; i < coreSize; i++) {

                Connection connection = DriverManager.getConnection(properties.getProperty("user.url"),properties.getProperty("user.name"),properties.getProperty("user.password"));
                connections.add(connection);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Connection getCollection(){
        try{
            lock.lock();
            if(connections.peek() == null && count < maxSize){
                Connection connection = DriverManager.getConnection(properties.getProperty("user.url"),properties.getProperty("user.name"),properties.getProperty("user.password"));
                connections.add(connection);
            }
            Connection conn = connections.poll();
            count++;
            return conn;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void release(Connection connection){
        try{
            lock.lock();
            if( connection != null){
               if(connections.size()<=coreSize) {
                   connections.add(connection);
               }
            }else{
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void shotDown(){
        try{
            lock.lock();
            if(connections.size() > 0){
                Connection connection = connections.poll();
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
