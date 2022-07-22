package com.ebs.marketplace.mapper;

import com.ebs.marketplace.model.CheckedFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CheckedFilesMapper {

    void insert(CheckedFiles checkedFiles);

    int existsByName(@Param("file_name") String fileName);
}
