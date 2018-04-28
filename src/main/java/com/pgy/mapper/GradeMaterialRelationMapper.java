package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.material.bean.GradeMaterialRelation;

/**
 * Grade-Material relation mapper.
 *
 * @author Felix
 */
@Mapper
public interface GradeMaterialRelationMapper {
    List<GradeMaterialRelation> getRelationsByMaterials(@Param("materialIds") List<Long> materialIds);

    List<GradeMaterialRelation> getRelationsByGrades(@Param("gradeIds") List<Long> gradeIds);

    int create(GradeMaterialRelation entity);

    void deleteByMaterials(@Param("materialIds") List<Long> materialIds);

    void deleteByGrades(@Param("gradeIds") List<Long> gradeIds);
}
