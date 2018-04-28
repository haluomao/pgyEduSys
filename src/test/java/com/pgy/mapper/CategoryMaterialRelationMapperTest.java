package com.pgy.mapper;

import java.util.Collections;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.common.bean.Status;
import com.pgy.material.bean.CategoryMaterialRelation;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link CategoryMaterialRelationMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "category_material")
public class CategoryMaterialRelationMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 1L;

    @Autowired
    private CategoryMaterialRelationMapper relationMapper;

    @Test
    public void getRelationsByMaterials() {
        assertEquals(Lists.newArrayList(buildCategoryMaterialRelation1()),
                relationMapper.getRelationsByMaterials(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void getRelationsByCategorys() {
        assertEquals(Lists.newArrayList(buildCategoryMaterialRelation1()),
                relationMapper.getRelationsByCategorys(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void create() {
        CategoryMaterialRelation categoryMaterialRelation = buildCategoryMaterialRelation2();
        relationMapper.create(categoryMaterialRelation);
    }

    @Test
    public void deleteByMaterials() {
        relationMapper.deleteByMaterials(Lists.newArrayList(ID_1));
        assertEquals(Collections.EMPTY_LIST,
                relationMapper.getRelationsByMaterials(Lists.newArrayList(ID_1)));
    }

    @Test
    public void deleteByCategorys() {
        relationMapper.deleteByCategorys(Lists.newArrayList(ID_1));
        assertEquals(Collections.EMPTY_LIST,
                relationMapper.getRelationsByCategorys(Lists.newArrayList(ID_1)));
    }

    public CategoryMaterialRelation buildCategoryMaterialRelation1() {
        CategoryMaterialRelation relation = new CategoryMaterialRelation();
        relation.setId(ID_1);
        relation.setCategoryId(ID_1);
        relation.setMaterialId(ID_1);
        relation.setStatus(Status.ENABLED);
        return relation;
    }

    public CategoryMaterialRelation buildCategoryMaterialRelation2() {
        CategoryMaterialRelation relation = new CategoryMaterialRelation();
        relation.setId(ID_2);
        relation.setCategoryId(ID_2);
        relation.setMaterialId(ID_2);
        relation.setStatus(Status.ENABLED);
        return relation;
    }
}
