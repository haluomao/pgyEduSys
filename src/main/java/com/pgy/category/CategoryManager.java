package com.pgy.category;

import java.util.List;

import com.pgy.category.bean.Category;

/**
 * The category manager.
 *
 * @author Felix
 */
public interface CategoryManager {

    List<Category> getCategories(List<Long> ids);

    Category detail(long id);

    int create(Category entity);

    int update(Category entity);

    void delete(List<Long> ids);
}
