package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.models.po.Userregister;

@Mapper
public interface UserMapper {
    @Select("select * from userregister where 用户账号 = #{username}")
    Userregister findByUserName(String username);
    @Insert("insert into userregister(用户账号,用户密码) values(#{username},#{password})")
    void add(String username, String password);
}
