package com.ebs.marketplace.mappers;

import com.ebs.marketplace.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (username, email, password) VALUES(#{username}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM users WHERE username = #{username} OR email = #{email}")
    User findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);


    @Select("SELECT COUNT(id) FROM users WHERE username = #{username} OR email = #{email}")
    int existsByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Select("SELECT COUNT(id) FROM users WHERE email = #{email}")
    int existsByEmail(@Param("email") String email);

    @Select("SELECT COUNT (id) FROM users WHERE username = #{username}")
    int existsByUsername(@Param("username") String username);
}
