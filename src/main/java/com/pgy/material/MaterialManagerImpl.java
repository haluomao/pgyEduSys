package com.pgy.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.mapper.MaterialMapper;
import com.pgy.material.bean.Material;

/**
 * The impl of {@link MaterialManager}.
 *
 * @author Felix
 */
@Component
public class MaterialManagerImpl implements MaterialManager {
    private final MaterialMapper materialMapper;

    @Autowired
    public MaterialManagerImpl(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    @Override
    public List<Material> getMaterials(List<Long> ids) {
        return materialMapper.getMaterials(ids);
    }

    @Override
    public List<Material> query(MaterialCriteria criteria) {
        return null;
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
