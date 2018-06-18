package com.pgy.controller.category;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.controller.category.bean.CategoryPagedRequest;
import com.pgy.controller.category.bean.CategoryVO;
import com.pgy.rest.IdRequest;
import com.pgy.rest.RestPageResponse;
import com.pgy.rest.RestResultResponse;

/**
 * The category controller.
 *
 * @author Felix
 */
@RequestMapping("/api/v1/category")
public interface CategoryController {

    @RequestMapping("/list")
    RestPageResponse<CategoryVO> list(CategoryPagedRequest request) throws Exception;

    @RequestMapping("/all")
    RestResultResponse<List<CategoryVO>> all() throws Exception;

    @RequestMapping("/detail")
    RestResultResponse<CategoryVO> detail(IdRequest idRequest) throws Exception;

    @RequestMapping("/create")
    RestResultResponse<Void> create(CategoryVO request) throws Exception;

    @RequestMapping("/update")
    RestResultResponse<Void> update(CategoryVO request) throws Exception;

    @RequestMapping("/delete")
    RestResultResponse<Void> delete(IdRequest idRequest) throws Exception;
}
