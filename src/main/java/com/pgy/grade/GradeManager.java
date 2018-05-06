package com.pgy.grade;

import java.util.List;

import com.pgy.grade.bean.Grade;

/**
 * The grade manager.
 *
 * @author Felix
 */
public interface GradeManager {

    List<Grade> getGrade(List<Long> ids);

    Grade detail(long id);

    int create(Grade entity);

    int update(Grade entity);

    void delete(List<Long> ids);
}
