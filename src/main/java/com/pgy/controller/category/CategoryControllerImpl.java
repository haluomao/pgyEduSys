package com.pgy.controller.category;

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
import com.pgy.category.CategoryManager;
import com.pgy.category.bean.Category;
import com.pgy.common.CollectionHelper;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.category.bean.CategoryPagedRequest;
import com.pgy.controller.category.bean.CategoryVO;
import com.pgy.rest.PageHelper;
import com.pgy.rest.RestPage;
import com.pgy.rest.RestPageResponse;
import com.pgy.rest.RestResponseFactory;

/**
 * The impl of {@link CategoryController}.
 *
 * @author Felix
 */
@RestController
public class CategoryControllerImpl implements CategoryController {
    private static final Log log = LogFactory.getLog(CategoryControllerImpl.class);

    private final CategoryManager categoryManager;

    @Autowired
    public CategoryControllerImpl(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public RestPageResponse<CategoryVO> list(@RequestBody CategoryPagedRequest request) throws Exception {
        log.info(new LogMessageBuilder("list category")
                .withParameter("request", request)
                .build());

        List<Category> categories = categoryManager.getCategories(Collections.EMPTY_LIST);
        return RestResponseFactory.newPagedResponse(new RestPage.Builder<CategoryVO>()
                .withBasePagedRequest(request)
                .withTotalCount(categories.size())
                .withResult(CollectionHelper.transform(
                        PageHelper.pageItems(categories, request.getPageNo(), request.getPageSize()),
                        new Function<Category, CategoryVO>() {
                            @Override
                            public CategoryVO apply(Category input) {
                                return CategoryVO.Builder.aCategoryRequest()
                                        .withCategory(input)
                                        .build();
                            }
                        }
                ))
                .build());
    }

    @Override
    public RestResultResponse<List<CategoryVO>> all() throws Exception {
        log.info(new LogMessageBuilder("list all categories")
                .build());

        List<Category> categories = categoryManager.getCategories(Collections.EMPTY_LIST);
        return RestResponseFactory.newSuccessfulResponse(CollectionHelper.transform(categories,
                new Function<Category, CategoryVO>() {
                    @Override
                    public CategoryVO apply(Category input) {
                        return CategoryVO.Builder.aCategoryRequest()
                                .withCategory(input)
                                .build();
                    }
                }));
    }

    @Override
    public RestResultResponse<CategoryVO> detail(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("get the detail of a category")
                .withParameter("request", idRequest)
                .build());
        Preconditions.checkArgument(idRequest.getId() > 0);
        Category category = categoryManager.detail(idRequest.getId());

        return RestResponseFactory.newSuccessfulResponse(
                CategoryVO.Builder.aCategoryRequest()
                        .withCategory(category)
                        .build());
    }

    @Override
    public RestResultResponse<Void> create(@RequestBody CategoryVO request) throws Exception {
        log.info(new LogMessageBuilder("create category")
                .withParameter("request", request)
                .build());

        categoryManager.create(request.buildCategory());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> update(@RequestBody CategoryVO request) throws Exception {
        log.info(new LogMessageBuilder("update category")
                .withParameter("request", request)
                .build());

        Preconditions.checkArgument(request.getId() > 0);
        categoryManager.update(request.buildCategory());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> delete(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("delete category")
                .withParameter("request", idRequest)
                .build());

        Preconditions.checkArgument(idRequest.getId() > 0);
        categoryManager.delete(Lists.newArrayList(idRequest.getId()));
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }
}
