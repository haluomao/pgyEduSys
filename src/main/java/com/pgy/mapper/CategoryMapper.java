package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.category.bean.Category;

/**
 * Category mapper.
 *
 * @author Felix
 */
@Mapper
public interface CategoryMapper {
    List<Category> getCategories(@Param("ids") List<Long> ids);

    int create(Category entity);

    int update(Category entity);

    void delete(List<Long> ids);

    Category detail(@Param("id") long id);
}
