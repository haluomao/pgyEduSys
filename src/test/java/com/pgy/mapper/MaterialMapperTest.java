package com.pgy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.Material;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.material.bean.PublicLevel;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link MaterialMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "materials")
public class MaterialMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

    @Autowired
    private MaterialMapper materialMapper;

    @Test
    public void getMaterials() {
        assertEquals(Lists.newArrayList(buildMaterial1()),
                materialMapper.getMaterials(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void detail() {
        assertEquals(null, materialMapper.detail(3L));
        assertEquals(buildMaterial1(), materialMapper.detail(ID_1));
    }

    @Test
    public void create() {
        Material material = buildMaterial2();
        materialMapper.create(material);
        // assertEquals(material, materialMapper.detail(material.getId()));
    }

    @Test
    public void update() {
        Material material = buildMaterial2();
        assertEquals(1, materialMapper.update(material));
    }

    @Test
    public void delete() {
        materialMapper.delete(Lists.newArrayList(ID_1));
        assertEquals(null, materialMapper.detail(ID_1));
    }

    @Test
    public void query() {
        assertEquals(Lists.newArrayList(buildMaterial1(), buildMaterial2()),
                materialMapper.query(buildMaterialCriteria()));
    }

    private MaterialCriteria buildMaterialCriteria() {
        return MaterialCriteria.Builder.aMaterialCriteria()
                .withName("ma")
                .build();
    }

    private Material buildMaterial1() {
        Material material = new Material();
        material.setId(ID_1);
        material.setName("material1");
        material.setDescription("desc1");
        material.setFileType(FileType.TXT);
        material.setUrl("url1");
        material.setIcon("icon1");
        material.setAuthorId(1);
        material.setUploaderId(1);
        material.setStatus(MaterialStatus.ENABLED);
        material.setPublicLevel(PublicLevel.PUBLIC);
        return material;
    }

    private Material buildMaterial2() {
        Material material = new Material();
        material.setId(ID_2);
        material.setName("material2");
        material.setDescription("desc2");
        material.setFileType(FileType.TXT);
        material.setUrl("url2");
        material.setIcon("icon2");
        material.setAuthorId(1);
        material.setUploaderId(1);
        material.setStatus(MaterialStatus.ENABLED);
        material.setPublicLevel(PublicLevel.PUBLIC);
        return material;
    }
}
