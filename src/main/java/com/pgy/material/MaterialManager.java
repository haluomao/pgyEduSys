package com.pgy.material;

import java.util.List;

import com.pgy.category.bean.Category;

/**
 * The material manager.
 *
 * @author Felix
 */
public interface MaterialManager {

    List<Category> getCategories(List<Long> ids);

    Category detail(long id);

    int create(Category entity);

    int update(Category entity);

    void delete(List<Long> ids);
}
