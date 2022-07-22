package com.ebs.marketplace.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckedFiles {
    private long id;
    private String fileName;

    public CheckedFiles(String fileName) {
        this.fileName = fileName;
    }
}


