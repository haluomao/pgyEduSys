package com.pgy.controller.material;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.common.CollectionHelper;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.material.bean.MaterialCriteria;
import com.pgy.controller.material.bean.MaterialPagedRequest;
import com.pgy.controller.material.bean.MaterialVO;
import com.pgy.material.MaterialManager;
import com.pgy.material.bean.FileType;
import com.pgy.material.bean.Material;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.rest.PageHelper;
import com.pgy.rest.RestPage;
import com.pgy.rest.RestPageResponse;
import com.pgy.rest.RestResponseFactory;

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
    public RestPageResponse<MaterialVO> list(@RequestBody MaterialPagedRequest request) throws Exception {
        log.info(new LogMessageBuilder("list material")
                .withParameter("request", request)
                .build());

        List<Material> categories = materialManager.query(MaterialCriteria.Builder.aMaterialCriteria()
                .withName(request.getName())
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
    public RestResultResponse<List<MaterialVO>> all() throws Exception {
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
    public RestResultResponse<MaterialVO> detail(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("get the detail of a category")
                .withParameter("request", idRequest)
                .build());
        Preconditions.checkArgument(idRequest.getId() > 0);
        Material material = materialManager.detail(idRequest.getId());

        return RestResponseFactory.newSuccessfulResponse(
                MaterialVO.Builder.aMaterialVO()
                        .withMaterial(material)
                        .build());
    }

    @Override
    public RestResultResponse<Void> create(@RequestBody MaterialVO request) throws Exception {
        log.info(new LogMessageBuilder("create category")
                .withParameter("request", request)
                .build());
        request.setStatus(MaterialStatus.ENABLED);
        request.setFileType(FileType.UNKNOWN);
        materialManager.create(request.buildMaterial());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> update(@RequestBody MaterialVO request) throws Exception {
        log.info(new LogMessageBuilder("update category")
                .withParameter("request", request)
                .build());

        Preconditions.checkArgument(request.getId() > 0);
        materialManager.update(request.buildMaterial());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> delete(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("delete category")
                .withParameter("request", idRequest)
                .build());

        Preconditions.checkArgument(idRequest.getId() > 0);
        materialManager.delete(Lists.newArrayList(idRequest.getId()));
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }
}
