package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Product {

    private long id;
    @NotNull(message = "Titlul nu poate fi gol")
    private String title;
    private String description;
    @NotNull(message = "Pretul nu poate fi gol")
    private double price;
    private String userUsername;
    private long likesCounter;
    private long dislikesCounter;
}
