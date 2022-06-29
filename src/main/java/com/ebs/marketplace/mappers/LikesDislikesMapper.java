package com.ebs.marketplace.mappers;

import com.ebs.marketplace.model.LikesDislikes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LikesDislikesMapper {

//    @Insert("INSERT INTO likes_dislikes VALUES (#{userId}, #{productId}, #{likeDislike})")
    void insert(LikesDislikes likesDislikes);

//    @Update("UPDATE likes_dislikes SET like_dislike = #{likeDislike} WHERE user_id = #{userId} AND product_id = #{productId}")
    void update(LikesDislikes likesDislikes);

//    @Delete("DELETE FROM likes_dislikes WHERE user_id = #{user_id} AND product_id = #{product_id}")
    void deleteByUserIdAndProductId(@Param("user_id") long userId, @Param("product_id") long productId);

//    @Select("SELECT COUNT (product_id) FROM likes_dislikes WHERE product_id = #{product_id} AND like_dislike = #{like_dislike}")
    long countByProductIdAndLikesDislikes(@Param("product_id") long productId, @Param("like_dislike") String likeDislike);

//    @Select("SELECT * FROM likes_dislikes WHERE user_id = #{user_id} AND product_id = #{product_id}")
//    @Results(value = {
//            @Result(property = "userId", column = "user_id"),
//            @Result(property="productId", column = "product_id"),
//            @Result(property="likeDislike", column = "like_dislike")
//    })
    LikesDislikes findByUserIdAndProductId(@Param("user_id") long userId, @Param("product_id") long productId);
}
