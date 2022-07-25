package com.ebs.marketplace.mapper;

import com.ebs.marketplace.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insert (Notification notification);

    List<Notification> findByUserId (@Param("user_id") long userId);

    Notification findById (@Param("id") long id);

    int existsById(@Param("id") long id);

    void seen (@Param("id") long id);

    void deleteById (@Param("id") long id);
}
