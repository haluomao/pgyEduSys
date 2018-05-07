package com.pgy.material;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pgy.common.CollectionHelper;
import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.mapper.CategoryMaterialRelationMapper;
import com.pgy.mapper.GradeMaterialRelationMapper;
import com.pgy.mapper.MaterialMapper;
import com.pgy.material.bean.CategoryMaterialRelation;
import com.pgy.material.bean.GradeMaterialRelation;
import com.pgy.material.bean.Material;

/**
 * The impl of {@link MaterialManager}.
 *
 * @author Felix
 */
@Component
public class MaterialManagerImpl implements MaterialManager {
    private final MaterialMapper materialMapper;
    private final GradeMaterialRelationMapper gradeMaterialRelationMapper;
    private final CategoryMaterialRelationMapper categoryMaterialRelationMapper;

    @Autowired
    public MaterialManagerImpl(MaterialMapper materialMapper,
            GradeMaterialRelationMapper gradeMaterialRelationMapper,
            CategoryMaterialRelationMapper categoryMaterialRelationMapper) {
        this.materialMapper = materialMapper;
        this.gradeMaterialRelationMapper = gradeMaterialRelationMapper;
        this.categoryMaterialRelationMapper = categoryMaterialRelationMapper;
    }

    @Override
    public List<Material> getMaterials(List<Long> ids) {
        return CollectionHelper.getNonNullList(materialMapper.getMaterials(ids));
    }

    @Override
    public List<Material> query(MaterialCriteria criteria) {
        List<Long> materialIdsFromCategory = null;
        List<Long> materialIdsFromGrade = null;
        if (criteria.getCategoryId() > 0) {
            materialIdsFromCategory = CollectionHelper.transform(
                    categoryMaterialRelationMapper.getRelationsByCategorys(
                            Lists.newArrayList(criteria.getCategoryId())),
                    new Function<CategoryMaterialRelation, Long>() {
                        @Override
                        public Long apply(CategoryMaterialRelation input) {
                            return input.getMaterialId();
                        }
                    });
        }
        if (criteria.getGradeId() > 0) {
            materialIdsFromGrade = CollectionHelper.transform(
                    gradeMaterialRelationMapper.getRelationsByGrades(
                            Lists.newArrayList(criteria.getGradeId())),
                    new Function<GradeMaterialRelation, Long>() {
                        @Override
                        public Long apply(GradeMaterialRelation input) {
                            return input.getMaterialId();
                        }
                    });
        }
        List<Long> ids;
        if (materialIdsFromCategory != null && materialIdsFromGrade != null) {
            ids = Lists.newArrayList(CollectionUtils.intersection(materialIdsFromCategory, materialIdsFromGrade));
        } else if (materialIdsFromCategory != null) {
            ids = materialIdsFromCategory;
        } else {
            ids = materialIdsFromGrade;
        }
        criteria.setIds(ids);
        return CollectionHelper.getNonNullList(materialMapper.query(criteria));
    }

    @Override
    public Material detail(long id) {
        return materialMapper.detail(id);
    }

    @Override
    public int create(Material entity) {
        return materialMapper.create(entity);
    }

    @Override
    public int update(Material entity) {
        return materialMapper.update(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        materialMapper.delete(ids);
    }
}
