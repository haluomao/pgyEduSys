package com.pgy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.common.bean.Status;
import com.pgy.grade.bean.Grade;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link GradeMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "grades")
public class GradeMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 1L;

    @Autowired
    private GradeMapper gradeMapper;

    @Test
    public void getCategories() {
        assertEquals(Lists.newArrayList(buildGrade1()),
                gradeMapper.getGrades(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void detail() {
        assertEquals(null, gradeMapper.detail(3L));
        assertEquals(buildGrade1(), gradeMapper.detail(ID_1));
    }

    @Test
    public void create() {
        Grade grade = buildGrade2();
        gradeMapper.create(grade);
        assertEquals(grade, gradeMapper.detail(grade.getId()));
    }

    @Test
    public void update() {
        Grade grade = buildGrade2();
        assertEquals(1, gradeMapper.update(grade));
    }

    @Test
    public void delete() {
        gradeMapper.delete(Lists.newArrayList(ID_1));
        assertEquals(null, gradeMapper.detail(ID_1));
    }

    public Grade buildGrade1() {
        Grade grade = new Grade();
        grade.setId(ID_1);
        grade.setGradeName("grade1");
        grade.setDescription("desc1");
        grade.setStatus(Status.ENABLED);
        return grade;
    }

    public Grade buildGrade2() {
        Grade grade = new Grade();
        grade.setId(ID_2);
        grade.setGradeName("grade2");
        grade.setDescription("desc2");
        grade.setStatus(Status.ENABLED);
        return grade;
    }
}
