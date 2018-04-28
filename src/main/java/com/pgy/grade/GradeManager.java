package com.pgy.grade;

import com.pgy.grade.bean.Grade;

/**
 * The grade manager.
 *
 * @author Felix
 */
public interface GradeManager {
    Grade detail(long id);

    int create(Grade entity);
}
