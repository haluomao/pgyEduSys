package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.material.bean.Material;

/**
 * Material mapper.
 *
 * @author Felix
 */
@Mapper
public interface MaterialMapper {
    List<Material> getMaterials(@Param("ids") List<Long> ids);

    int create(Material entity);

    int update(Material entity);

    void delete(List<Long> ids);

    Material detail(@Param("id") long id);
}
