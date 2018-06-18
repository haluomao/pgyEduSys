package com.pgy.material;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pgy.common.CollectionHelper;
import com.pgy.common.bean.Status;
import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.mapper.CategoryMaterialRelationMapper;
import com.pgy.mapper.GradeMaterialRelationMapper;
import com.pgy.mapper.MaterialMapper;
import com.pgy.material.bean.CategoryMaterialRelation;
import com.pgy.material.bean.GradeMaterialRelation;
import com.pgy.material.bean.Material;
import com.pgy.mybatis.PgyTransactionHelper;

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
    private final PgyTransactionHelper pgyTransactionHelper;

    @Autowired
    public MaterialManagerImpl(MaterialMapper materialMapper,
            GradeMaterialRelationMapper gradeMaterialRelationMapper,
            CategoryMaterialRelationMapper categoryMaterialRelationMapper,
            PgyTransactionHelper pgyTransactionHelper) {
        this.materialMapper = materialMapper;
        this.gradeMaterialRelationMapper = gradeMaterialRelationMapper;
        this.categoryMaterialRelationMapper = categoryMaterialRelationMapper;
        this.pgyTransactionHelper = pgyTransactionHelper;
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
        List<Long> ids = null;
        if (materialIdsFromCategory != null && materialIdsFromGrade != null) {
            ids = Lists.newArrayList(CollectionUtils.intersection(materialIdsFromCategory, materialIdsFromGrade));
        } else if (materialIdsFromCategory != null) {
            ids = materialIdsFromCategory;
        } else if (materialIdsFromGrade != null) {
            ids = materialIdsFromGrade;
        }

        if (ids != null && ids.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        criteria.setIds(ids);
        return CollectionHelper.getNonNullList(materialMapper.query(criteria));
    }

    @Override
    public Material detail(long id) {
        return materialMapper.detail(id);
    }

    @Override
    public long getGradeId(long materialId) {
        List<GradeMaterialRelation> relations = gradeMaterialRelationMapper.getRelationsByMaterials(
                Lists.<Long>newArrayList(materialId));
        return CollectionUtils.isNotEmpty(relations) ? relations.get(0).getGradeId() : 0L;
    }

    @Override
    public long getCategoryId(long materialId) {
        List<CategoryMaterialRelation> relations = categoryMaterialRelationMapper.getRelationsByMaterials(
                Lists.<Long>newArrayList(materialId));
        return CollectionUtils.isNotEmpty(relations) ? relations.get(0).getCategoryId() : 0L;
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
    public void delete(final List<Long> ids) {
        pgyTransactionHelper.runInTransaction(
                new Runnable() {
                    @Override
                    public void run() {
                        gradeMaterialRelationMapper.deleteByMaterials(ids);
                        categoryMaterialRelationMapper.deleteByMaterials(ids);
                        materialMapper.delete(ids);
                    }
                }
        );
    }

    @Override
    public void updateWithRelations(final Material entity, long gradeId, long categoryId) {
        final long materialId = entity.getId();
        final GradeMaterialRelation gradeMaterialRelation = new GradeMaterialRelation();
        gradeMaterialRelation.setMaterialId(materialId);
        gradeMaterialRelation.setGradeId(gradeId);
        gradeMaterialRelation.setStatus(Status.ENABLED);

        final CategoryMaterialRelation categoryMaterialRelation = new CategoryMaterialRelation();
        categoryMaterialRelation.setMaterialId(materialId);
        categoryMaterialRelation.setCategoryId(categoryId);
        categoryMaterialRelation.setStatus(Status.ENABLED);

        pgyTransactionHelper.runInTransaction(
                new Runnable() {
                    @Override
                    public void run() {
                        if (materialId > 0) {
                            materialMapper.update(entity);
                            gradeMaterialRelationMapper.deleteByMaterials(Lists.newArrayList(materialId));
                            categoryMaterialRelationMapper.deleteByMaterials(Lists.newArrayList(materialId));
                        } else {
                            materialMapper.create(entity);
                            gradeMaterialRelation.setMaterialId(entity.getId());
                            categoryMaterialRelation.setMaterialId(entity.getId());
                        }

                        gradeMaterialRelationMapper.create(gradeMaterialRelation);
                        categoryMaterialRelationMapper.create(categoryMaterialRelation);
                    }
                });
    }
}
