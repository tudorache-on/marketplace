package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private long id;
    private String title;
    private String description;
    private double price;
    private String userUsername;
    private long likesCounter;
    private long dislikesCounter;
}
