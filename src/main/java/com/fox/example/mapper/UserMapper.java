package com.fox.example.mapper;

import com.fox.example.annontation.Select;
import com.fox.example.doman.User;

public interface UserMapper {

    /**
     * 查询id
     * @param id
     * @return
     */
    @Select(sql = "select id,user_code from user_info where id = ?")
    User selectById(String id);
}
