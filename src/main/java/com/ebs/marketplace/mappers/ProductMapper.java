package com.ebs.marketplace.mappers;

import com.ebs.marketplace.model.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO products (title, description, price, userUsername, likesCounter, dislikesCounter) VALUES(#{title}, #{description}, #{price}, #{userUsername}, #{likesCounter}, #{dislikesCounter})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Product product);

    @Update("UPDATE products SET title = #{title}, description = #{description}, price = #{price}, userUsername = #{userUsername}, likesCounter = #{likesCounter}, dislikesCounter = #{dislikesCounter} WHERE id = #{id}")
    void update(Product product);

    @Select("SELECT * FROM products WHERE userUsername = #{username}")
    List<Product> findByUserUsername(@Param("username") String currentUsername);

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product findById (@Param("id") long id);

    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteById(@Param("id") long id);

    @Select("SELECT * FROM products")
    List<Product> findAll(RowBounds rowBounds);
}
