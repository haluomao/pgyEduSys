package com.pgy.material;

import java.util.List;

import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.material.bean.Material;

/**
 * The material manager.
 *
 * @author Felix
 */
public interface MaterialManager {

    List<Material> getMaterials(List<Long> ids);

    List<Material> query(MaterialCriteria criteria);

    Material detail(long id);

    int create(Material entity);

    int update(Material entity);

    void delete(List<Long> ids);
}
