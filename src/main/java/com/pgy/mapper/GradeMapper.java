package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.grade.bean.Grade;

/**
 * Grade mapper.
 *
 * @author Felix
 */
@Mapper
public interface GradeMapper {
    List<Grade> getGrades(@Param("ids") List<Long> ids);

    int create(Grade entity);

    int update(Grade entity);

    void delete(List<Long> ids);

    Grade detail(@Param("id") long id);
}
