package com.ebs.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
    private long id;
    private String message;
    private boolean read;
    private long userId;
}
