package com.pgy.grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pgy.grade.bean.Grade;
import com.pgy.mapper.GradeMapper;

/**
 * The impl of {@link GradeManager}.
 *
 * @author Felix
 */
public class GradeManagerImpl implements GradeManager {
    private final GradeMapper gradeMapper;

    @Autowired
    public GradeManagerImpl(GradeMapper gradeMapper) {
        this.gradeMapper = gradeMapper;
    }

    @Override
    public List<Grade> getGrade(List<Long> ids) {
        return gradeMapper.getGrades(ids);
    }

    @Override
    public Grade detail(long id) {
        return gradeMapper.detail(id);
    }

    @Override
    public int create(Grade entity) {
        return gradeMapper.create(entity);
    }

    @Override
    public int update(Grade entity) {
        return gradeMapper.update(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        gradeMapper.delete(ids);
    }
}
