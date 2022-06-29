package com.ebs.marketplace.mapper;

import com.ebs.marketplace.model.LikesDislikes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikesDislikesMapper {

    void insert(LikesDislikes likesDislikes);

    void update(LikesDislikes likesDislikes);

    void deleteByUserIdAndProductId(@Param("user_id") long userId, @Param("product_id") long productId);

    long countByProductIdAndLikesDislikes(@Param("product_id") long productId, @Param("like_dislike") String likeDislike);

    LikesDislikes findByUserIdAndProductId(@Param("user_id") long userId, @Param("product_id") long productId);
}
