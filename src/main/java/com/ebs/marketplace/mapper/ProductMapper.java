package com.ebs.marketplace.mapper;

import com.ebs.marketplace.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ProductMapper {
    void insert(Product product);

    void update(Product product);

    List<Product> findByUserUsername(@Param("username") String currentUsername);

    Product findById(@Param("id") long id);

    void deleteById(@Param("id") long id);

    List<Product> findAll(RowBounds rowBounds);

    int existsById(@Param("id") long id);
}
