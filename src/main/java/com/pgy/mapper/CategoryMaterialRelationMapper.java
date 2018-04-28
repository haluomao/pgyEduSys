package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.material.bean.CategoryMaterialRelation;

/**
 * Category-Material relation mapper.
 *
 * @author Felix
 */
@Mapper
public interface CategoryMaterialRelationMapper {
    List<CategoryMaterialRelation> getRelationsByMaterials(@Param("materialIds") List<Long> materialIds);

    List<CategoryMaterialRelation> getRelationsByCategorys(@Param("categoryIds") List<Long> categoryIds);

    int create(CategoryMaterialRelation entity);

    void deleteByMaterials(@Param("materialIds") List<Long> materialIds);

    void deleteByCategorys(@Param("categoryIds") List<Long> categoryIds);
}
