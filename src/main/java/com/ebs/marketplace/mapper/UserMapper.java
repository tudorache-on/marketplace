package com.ebs.marketplace.mapper;

import com.ebs.marketplace.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insert(User user);

    User findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    User findByUsername(@Param("username") String username);

    User findById (@Param("id") long id);

    int existsByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    int existsByEmail(@Param("email") String email);

    int existsByUsername(@Param("username") String username);
}
