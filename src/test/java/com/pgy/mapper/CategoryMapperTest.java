package com.pgy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.category.bean.Category;
import com.pgy.common.bean.Status;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link CategoryMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "categories")
public class CategoryMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 1L;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void getCategories() {
        assertEquals(Lists.newArrayList(buildCategory1()),
                categoryMapper.getCategories(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void detail() {
        assertEquals(null, categoryMapper.detail(3L));
        assertEquals(buildCategory1(), categoryMapper.detail(ID_1));
    }

    @Test
    public void create() {
        Category category = buildCategory2();
        categoryMapper.create(category);
        assertEquals(category, categoryMapper.detail(category.getId()));
    }

    @Test
    public void update() {
        Category category = buildCategory2();
        assertEquals(1, categoryMapper.update(category));
    }

    @Test
    public void delete() {
        categoryMapper.delete(Lists.newArrayList(ID_1));
        assertEquals(null, categoryMapper.detail(ID_1));
    }

    public Category buildCategory1() {
        Category category = new Category();
        category.setId(ID_1);
        category.setName("category1");
        category.setDescription("desc1");
        category.setPrice(100);
        category.setStatus(Status.ENABLED);
        return category;
    }

    public Category buildCategory2() {
        Category category = new Category();
        category.setId(ID_2);
        category.setName("category2");
        category.setDescription("desc2");
        category.setPrice(200);
        category.setStatus(Status.ENABLED);
        return category;
    }
}
