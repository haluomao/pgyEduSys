package com.pgy.category;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgy.category.bean.Category;
import com.pgy.mapper.CategoryMapper;

/**
 * The impl of {@link CategoryManager}.
 *
 * @author Felix
 */
@Component
public class CategoryManagerImpl implements CategoryManager {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryManagerImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> getCategories(List<Long> ids) {
        return categoryMapper.getCategories(ids);
    }

    @Override
    public Category detail(long id) {
        return categoryMapper.detail(id);
    }

    @Override
    public int create(Category entity) {
        return categoryMapper.create(entity);
    }

    @Override
    public int update(Category entity) {
        return categoryMapper.update(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            categoryMapper.delete(ids);
        }
    }
}
