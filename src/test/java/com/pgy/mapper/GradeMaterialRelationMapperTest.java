package com.pgy.mapper;

import java.util.Collections;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.common.bean.Status;
import com.pgy.material.bean.GradeMaterialRelation;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link GradeMaterialRelationMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "grade_material")
public class GradeMaterialRelationMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 1L;

    @Autowired
    private GradeMaterialRelationMapper relationMapper;

    @Test
    public void getRelationsByMaterials() {
        assertEquals(Lists.newArrayList(buildGradeMaterialRelation1()),
                relationMapper.getRelationsByMaterials(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void getRelationsByGrades() {
        assertEquals(Lists.newArrayList(buildGradeMaterialRelation1()),
                relationMapper.getRelationsByGrades(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void create() {
        GradeMaterialRelation gradeMaterialRelation = buildGradeMaterialRelation2();
        relationMapper.create(gradeMaterialRelation);
    }

    @Test
    public void deleteByMaterials() {
        relationMapper.deleteByMaterials(Lists.newArrayList(ID_1));
        assertEquals(Collections.EMPTY_LIST,
                relationMapper.getRelationsByMaterials(Lists.newArrayList(ID_1)));
    }

    @Test
    public void deleteByGrades() {
        relationMapper.deleteByGrades(Lists.newArrayList(ID_1));
        assertEquals(Collections.EMPTY_LIST,
                relationMapper.getRelationsByGrades(Lists.newArrayList(ID_1)));
    }

    public GradeMaterialRelation buildGradeMaterialRelation1() {
        GradeMaterialRelation relation = new GradeMaterialRelation();
        relation.setId(ID_1);
        relation.setGradeId(ID_1);
        relation.setMaterialId(ID_1);
        relation.setStatus(Status.ENABLED);
        return relation;
    }

    public GradeMaterialRelation buildGradeMaterialRelation2() {
        GradeMaterialRelation relation = new GradeMaterialRelation();
        relation.setId(ID_2);
        relation.setGradeId(ID_2);
        relation.setMaterialId(ID_2);
        relation.setStatus(Status.ENABLED);
        return relation;
    }
}
