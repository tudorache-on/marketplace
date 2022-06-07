package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "market_products")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String title;
    private String description;
    private double price;
    private String userEmail;
    private String likes;
    @ColumnDefault("0")
    private int likesCounter;
}
