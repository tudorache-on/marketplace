package com.ebs.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDislikes {

    private long userId;

    private long productId;

    private String likeDislike;

}
