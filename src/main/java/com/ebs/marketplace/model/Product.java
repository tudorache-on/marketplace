package com.ebs.marketplace.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @CsvBindByName(column = "Id")
    private long id;
    @CsvBindByName(column = "Title")
    private String title;
    @CsvBindByName(column = "Description")
    private String description;
    @CsvBindByName(column = "Price")
    private double price;
    @CsvBindByName(column = "Username")
    private String userUsername;
    @CsvBindByName(column = "LikesCounter")
    private long likesCounter;
    @CsvBindByName(column = "DislikesCounter")
    private long dislikesCounter;

    public String toCsv() {
        return id + "," + title + "," + description + "," + price + "," + userUsername + "," + likesCounter + "," + dislikesCounter + "\n";
    }

    public Product(String title, String description, double price, String userUsername) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.userUsername = userUsername;
    }
}


