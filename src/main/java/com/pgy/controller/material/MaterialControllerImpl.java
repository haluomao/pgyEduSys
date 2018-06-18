package com.pgy.controller.material;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.auth.bean.CustomUser;
import com.pgy.common.CollectionHelper;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.controller.material.bean.MaterialPagedRequest;
import com.pgy.controller.material.bean.MaterialVO;
import com.pgy.material.MaterialManager;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.Material;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.material.bean.PublicLevel;
import com.pgy.rest.IdRequest;
import com.pgy.rest.PageHelper;
import com.pgy.rest.RestPage;
import com.pgy.rest.RestPageResponse;
import com.pgy.rest.RestResponseFactory;
import com.pgy.rest.RestResultResponse;

/**
 * The impl of {@link MaterialController}.
 *
 * @author Felix
 */
@RestController
public class MaterialControllerImpl implements MaterialController {
    private static final Log log = LogFactory.getLog(MaterialControllerImpl.class);

    private final MaterialManager materialManager;

    @Autowired
    public MaterialControllerImpl(MaterialManager materialManager) {
        this.materialManager = materialManager;
    }

    @Override
    public RestPageResponse<MaterialVO> list(@AuthenticationPrincipal CustomUser loginUser,
            @RequestBody MaterialPagedRequest request) throws Exception {
        log.info(new LogMessageBuilder("list material")
                .withParameter("request", request)
                .build());

        List<Material> categories = materialManager.query(MaterialCriteria.Builder.aMaterialCriteria()
                .withName(request.getName())
                .withPublicLevel(request.getPublicLevel())
                .withAuthorId(request.getAuthorId())
                .withGradeId(request.getGradeId())
                .withCategoryId(request.getCategoryId())
                .withTeachType(request.getTeachType())
                .build());
        return RestResponseFactory.newPagedResponse(new RestPage.Builder<MaterialVO>()
                .withBasePagedRequest(request)
                .withTotalCount(categories.size())
                .withResult(CollectionHelper.transform(
                        PageHelper.pageItems(categories, request.getPageNo(), request.getPageSize()),
                        new Function<Material, MaterialVO>() {
                            @Override
                            public MaterialVO apply(Material input) {
                                return MaterialVO.Builder.aMaterialVO()
                                        .withMaterial(input)
                                        .build();
                            }
                        }
                ))
                .build());
    }

    @Override
    public RestResultResponse<List<MaterialVO>> all(@AuthenticationPrincipal CustomUser loginUser) throws Exception {
        log.info(new LogMessageBuilder("list all materials")
                .build());

        List<Material> materials = materialManager.getMaterials(Collections.EMPTY_LIST);
        return RestResponseFactory.newSuccessfulResponse(CollectionHelper.transform(materials,
                new Function<Material, MaterialVO>() {
                    @Override
                    public MaterialVO apply(Material input) {
                        return MaterialVO.Builder.aMaterialVO()
                                .withMaterial(input)
                                .build();
                    }
                }));
    }

    @Override
    public RestResultResponse<MaterialVO> detail(@AuthenticationPrincipal CustomUser loginUser,
            @RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("get the detail of a category")
                .withParameter("request", idRequest)
                .build());

        long materialId = idRequest.getId();
        Preconditions.checkArgument(materialId > 0);
        Material material = materialManager.detail(materialId);
        long gradeId = materialManager.getGradeId(materialId);
        long categoryId = materialManager.getCategoryId(materialId);
        return RestResponseFactory.newSuccessfulResponse(
                MaterialVO.Builder.aMaterialVO()
                        .withMaterial(material)
                        .withGradeId(gradeId)
                        .withCategoryId(categoryId)
                        .build());
    }

    @Override
    public RestResultResponse<Void> create(@AuthenticationPrincipal CustomUser loginUser,
            @RequestBody MaterialVO request) throws Exception {
        log.info(new LogMessageBuilder("create material.")
                .withParameter("request", request)
                .build());
        Preconditions.checkNotNull(loginUser);
        Preconditions.checkArgument(request.getCategoryId() > 0);
        Preconditions.checkArgument(request.getCategoryId() > 0);

        request.setStatus(MaterialStatus.ENABLED);
        request.setFileType(FileType.UNKNOWN);
        Material material = request.buildMaterial();
        material.setUploaderId(loginUser.getAccountId());
        material.setId(0L);
        material.setPublicLevel(PublicLevel.PUBLIC);
        materialManager.updateWithRelations(material, request.getGradeId(), request.getCategoryId());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> update(@AuthenticationPrincipal CustomUser loginUser,
            @RequestBody MaterialVO request) throws Exception {
        log.info(new LogMessageBuilder("update category")
                .withParameter("request", request)
                .build());

        Preconditions.checkArgument(request.getId() > 0);
        Preconditions.checkArgument(request.getGradeId() > 0);
        Preconditions.checkArgument(request.getCategoryId() > 0);

        Material material = request.buildMaterial();
        material.setPublicLevel(PublicLevel.PUBLIC);
        materialManager.updateWithRelations(material, request.getGradeId(), request.getCategoryId());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> delete(@AuthenticationPrincipal CustomUser loginUser,
            @RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("delete category")
                .withParameter("request", idRequest)
                .build());

        Preconditions.checkArgument(idRequest.getId() > 0);
        materialManager.delete(Lists.newArrayList(idRequest.getId()));
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public ResponseEntity<byte[]> download(@AuthenticationPrincipal CustomUser loginUser,
            @RequestParam(value = "id", required = true) Long materialId) throws Exception {
        log.info(new LogMessageBuilder("download material.")
                .withParameter("request", materialId)
                .build());

        Preconditions.checkArgument(materialId > 0);
        Preconditions.checkArgument(loginUser.getUserId() > 0);
        // check auth.

        Material material = materialManager.detail(materialId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text");

        return new ResponseEntity(getFileInBytes(material), headers, HttpStatus.OK);
    }

    private byte[] getFileInBytes(Material material) {
        File file = new File(material.getUrl());
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            byte[] buf = new byte[input.available()];
            input.read(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
